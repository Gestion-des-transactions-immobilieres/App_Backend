package com.immobiler.ProjectGeoInfo.mappers;

import com.immobiler.ProjectGeoInfo.Entities.*;
import com.immobiler.ProjectGeoInfo.Repositories.*;
import com.immobiler.ProjectGeoInfo.dtos.AnnonceRequestDTO;
import com.immobiler.ProjectGeoInfo.dtos.AnnonceResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnnonceMapper {

    private final AnnonceRepository annonceRepository;
    private final AppUserRepository appUserRepository;
    private final CommuneRepository communeRepository;
    private final TypeBienRepository typeBienRepository;
    private final TypeOperationRepository typeOperationRepository;
    private final StatutRepository statutRepository;


    public Annonce FromAnnonceRequestDTO(AnnonceRequestDTO annonceRequestDTO){
        Annonce annonce=new Annonce();
        BeanUtils.copyProperties(annonceRequestDTO,annonce);

        try {
            annonce.setLocation(annonceRequestDTO.getLocation());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid WKT format: " + annonceRequestDTO.getLocation());
        }

        Citoyen citoyen= (Citoyen) appUserRepository.findById(annonceRequestDTO.getCitoyenId())
                .orElseThrow(()-> new IllegalArgumentException("Citoyen with ID " + annonceRequestDTO.getCitoyenId() + " not found"));

        Commune commune= communeRepository.findById(annonceRequestDTO.getCommuneId())
                .orElseThrow(()-> new IllegalArgumentException("Commune with ID " + annonceRequestDTO.getCommuneId() +" not found"));

        TypeBien typeBien = typeBienRepository.findByNom(annonceRequestDTO.getTypeBienName())
                .orElseThrow(() -> new IllegalArgumentException("TypeBien with name " + annonceRequestDTO.getTypeBienName() + " not found"));

        TypeOperation typeOperation = typeOperationRepository.findByNom(annonceRequestDTO.getTypeOperationName())
                .orElseThrow(() -> new IllegalArgumentException("TypeOperation with name " + annonceRequestDTO.getTypeOperationName() + " not found"));

        Statut statut = statutRepository.findByNom(annonceRequestDTO.getStatutName())
                .orElseThrow(() -> new IllegalArgumentException("Statut with name " + annonceRequestDTO.getStatutName() + " not found"));

        annonce.setCitoyen(citoyen);
        annonce.setCommune(commune);
        annonce.setTypeBien(typeBien);
        annonce.setTypeOperation(typeOperation);
        annonce.setStatut(statut);

        return annonce;
    }

    public AnnonceResponseDTO FromAnnonce(Annonce annonce){
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
