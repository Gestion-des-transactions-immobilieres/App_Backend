package com.immobiler.ProjectGeoInfo.Services;

import com.immobiler.ProjectGeoInfo.Entities.*;
import com.immobiler.ProjectGeoInfo.Repositories.*;
import com.immobiler.ProjectGeoInfo.dtos.AnnonceRequestDTO;
import com.immobiler.ProjectGeoInfo.dtos.AnnonceResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final AppUserRepository appUserRepository;
    private final CommuneRepository communeRepository;
    private final TypeBienRepository typeBienRepository;
    private final TypeOperationRepository typeOperationRepository;
    private final StatutRepository statutRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Récupérer une annonce par ID
    public AnnonceResponseDTO getAnnonceById(Long id) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Annonce with ID " + id + " not found"));
        return convertToResponseDTO(annonce);
    }

    // Récupérer toutes les annonces
    public List<AnnonceResponseDTO> getAllAnnonces() {
        return annonceRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Récupérer les annonces par commune
    public List<AnnonceResponseDTO> getAnnonceByCommune(String communeName) {
        Commune commune = communeRepository.findByNom(communeName)
                .orElseThrow(() -> new IllegalArgumentException("Commune with name " + communeName + " not found"));
        return annonceRepository.findByCommune(commune).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Récupérer les annonces par type de bien
    public List<AnnonceResponseDTO> getAnnoncesByTypeBien(String typeBienName) {
        TypeBien typeBien = typeBienRepository.findByNom(typeBienName)
                .orElseThrow(() -> new IllegalArgumentException("TypeBien with name " + typeBienName + " not found"));
        return annonceRepository.findByTypeBien(typeBien).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Récupérer les annonces par type d'opération
    public List<AnnonceResponseDTO> getAnnoncesByTypeOperation(String typeOperationName) {
        TypeOperation typeOperation = typeOperationRepository.findByNom(typeOperationName)
                .orElseThrow(() -> new IllegalArgumentException("TypeOperation with name " + typeOperationName + " not found"));
        return annonceRepository.findByTypeOperation(typeOperation).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Récupérer les annonces par ID du citoyen
    public List<AnnonceResponseDTO> getAnnonceByUserId(Long citoyenId) {
        Citoyen citoyen = (Citoyen) appUserRepository.findById(citoyenId)
                .orElseThrow(() -> new IllegalArgumentException("Citoyen with ID " + citoyenId + " not found"));
        return annonceRepository.findByCitoyen(citoyen).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Récupérer les annonces par statut
    public List<AnnonceResponseDTO> getAnnonceByStatut(String statutName) {
        Statut statut = statutRepository.findByNom(statutName)
                .orElseThrow(() -> new IllegalArgumentException("Statut with name " + statutName + " not found"));
        return annonceRepository.findByStatut(statut).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Créer une annonce
    public AnnonceResponseDTO createAnnonce(AnnonceRequestDTO requestDTO) {
        Citoyen citoyen = (Citoyen) appUserRepository.findById(requestDTO.getCitoyenId())
                .orElseThrow(() -> new IllegalArgumentException("Citoyen with ID " + requestDTO.getCitoyenId() + " not found"));

        Commune commune = communeRepository.findById(requestDTO.getCommuneId())
                .orElseThrow(() -> new IllegalArgumentException("Commune with ID " + requestDTO.getCommuneId() + " not found"));

        TypeBien typeBien = typeBienRepository.findByNom(requestDTO.getTypeBienName())
                .orElseThrow(() -> new IllegalArgumentException("TypeBien with name " + requestDTO.getTypeBienName() + " not found"));

        TypeOperation typeOperation = typeOperationRepository.findByNom(requestDTO.getTypeOperationName())
                .orElseThrow(() -> new IllegalArgumentException("TypeOperation with name " + requestDTO.getTypeOperationName() + " not found"));

        Statut statut = statutRepository.findByNom(requestDTO.getStatutName())
                .orElseThrow(() -> new IllegalArgumentException("Statut with name " + requestDTO.getStatutName() + " not found"));

        Annonce annonce = new Annonce();
        annonce.setSurface(requestDTO.getSurface());
        annonce.setPrix(requestDTO.getPrix());
        annonce.setDescription(requestDTO.getDescription());

        try {
            annonce.setLocation(requestDTO.getLocation());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid WKT format: " + requestDTO.getLocation());
        }

        annonce.setCitoyen(citoyen);
        annonce.setCommune(commune);
        annonce.setTypeBien(typeBien);
        annonce.setTypeOperation(typeOperation);
        annonce.setStatut(statut);
        annonce.setDateOffre(LocalDate.now());

        Annonce savedAnnonce = annonceRepository.save(annonce);
        return convertToResponseDTO(savedAnnonce);
    }

    // Modifier une annonce existante
    public AnnonceResponseDTO updateAnnonce(Long id, AnnonceRequestDTO requestDTO) {
        Annonce existingAnnonce = annonceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Annonce with ID " + id + " not found"));

        existingAnnonce.setSurface(requestDTO.getSurface() != 0 ? requestDTO.getSurface() : existingAnnonce.getSurface());
        existingAnnonce.setPrix(requestDTO.getPrix() != 0 ? requestDTO.getPrix() : existingAnnonce.getPrix());
        existingAnnonce.setDescription(requestDTO.getDescription() != null ? requestDTO.getDescription() : existingAnnonce.getDescription());

        if (requestDTO.getLocation() != null) {
            try {
                existingAnnonce.setLocation(requestDTO.getLocation());
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid WKT format: " + requestDTO.getLocation());
            }
        }

        if (requestDTO.getStatutName() != null) {
            Statut statut = statutRepository.findByNom(requestDTO.getStatutName())
                    .orElseThrow(() -> new IllegalArgumentException("Statut with name " + requestDTO.getStatutName() + " not found"));
            existingAnnonce.setStatut(statut);
        }

        if (requestDTO.getTypeBienName() != null) {
            TypeBien typeBien = typeBienRepository.findByNom(requestDTO.getTypeBienName())
                    .orElseThrow(() -> new IllegalArgumentException("TypeBien with name " + requestDTO.getTypeBienName() + " not found"));
            existingAnnonce.setTypeBien(typeBien);
        }

        if (requestDTO.getTypeOperationName() != null) {
            TypeOperation typeOperation = typeOperationRepository.findByNom(requestDTO.getTypeOperationName())
                    .orElseThrow(() -> new IllegalArgumentException("TypeOperation with name " + requestDTO.getTypeOperationName() + " not found"));
            existingAnnonce.setTypeOperation(typeOperation);
        }

        Annonce updatedAnnonce = annonceRepository.save(existingAnnonce);
        return convertToResponseDTO(updatedAnnonce);
    }

    // Supprimer une annonce
    @Transactional
    public void deleteAnnonce(Long id) {
        Annonce existingAnnonce = annonceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Annonce with ID " + id + " not found"));

        existingAnnonce.getDemandes().clear();
        existingAnnonce.getJustificatif().clear();
        existingAnnonce.getPhotos().clear();

        annonceRepository.save(existingAnnonce);
        annonceRepository.delete(existingAnnonce);
    }

    // Convertir une entité Annonce en DTO de réponse
    private AnnonceResponseDTO convertToResponseDTO(Annonce annonce) {
        return new AnnonceResponseDTO(
                annonce.getId(),
                annonce.getSurface(),
                annonce.getPrix(),
                annonce.getDescription(),
                annonce.getLocation(),
                annonce.getCommune().getNom(),
                annonce.getTypeBien().getNom(),
                annonce.getTypeOperation().getNom(),
                annonce.getStatut().getNom(),
                annonce.getDateOffre()
        );
    }
}
