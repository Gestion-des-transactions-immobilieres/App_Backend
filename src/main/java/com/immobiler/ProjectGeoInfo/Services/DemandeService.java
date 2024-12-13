package com.immobiler.ProjectGeoInfo.Services;

import com.immobiler.ProjectGeoInfo.Entities.Demande;
import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Entities.Citoyen;
import com.immobiler.ProjectGeoInfo.Repositories.DemandeRepository;
import com.immobiler.ProjectGeoInfo.Repositories.AnnonceRepository;
import com.immobiler.ProjectGeoInfo.Repositories.CitoyenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class DemandeService {

    private DemandeRepository demandeRepository;
    private AnnonceRepository annonceRepository;
    private CitoyenRepository citoyenRepository;

    // Create a new Demande
    public Demande createDemande(Demande demande, Long annonceId, Long citoyenId) {
        Optional<Annonce> annonceOptional = annonceRepository.findById(annonceId);
        Optional<Citoyen> citoyenOptional = citoyenRepository.findById(citoyenId);

        if (annonceOptional.isPresent() && citoyenOptional.isPresent()) {
            Annonce annonce = annonceOptional.get();
            Citoyen citoyen = citoyenOptional.get();
            demande.setAnnonce(annonce);
            demande.setCitoyen(citoyen);
            demande.setDateDemande(demande.getDateDemande() != null ? demande.getDateDemande() : LocalDate.now());
            return demandeRepository.save(demande);
        } else {
            throw new IllegalArgumentException("Annonce or Citoyen not found for the given IDs.");
        }
    }

    // Get Demandes by Annonce ID
    public List<Demande> getDemandesByAnnonceId(Long annonceId) {
        Optional<Annonce> annonceOptional = annonceRepository.findById(annonceId);
        if (annonceOptional.isPresent()) {
            return demandeRepository.findByAnnonce(annonceOptional.get());
        } else {
            throw new IllegalArgumentException("Annonce with ID " + annonceId + " not found.");
        }
    }

    // Get Demandes by Citoyen ID
    public List<Demande> getDemandesByCitoyenId(Long citoyenId) {
        Optional<Citoyen> citoyenOptional = citoyenRepository.findById(citoyenId);
        if (citoyenOptional.isPresent()) {
            return demandeRepository.findByCitoyen(citoyenOptional.get());
        } else {
            throw new IllegalArgumentException("Citoyen with ID " + citoyenId + " not found.");
        }
    }

    // Update an existing Demande
    public Demande updateDemande(Long id, Demande newDemande) {
        Optional<Demande> demandeOptional = demandeRepository.findById(id);
        if (demandeOptional.isPresent()) {
            Demande existingDemande = demandeOptional.get();

            // Update attributes only if they are not null
            existingDemande.setEmail(
                    newDemande.getEmail() != null ? newDemande.getEmail() : existingDemande.getEmail()
            );
            existingDemande.setTelephone(
                    newDemande.getTelephone() != null ? newDemande.getTelephone() : existingDemande.getTelephone()
            );
            existingDemande.setFullName(
                    newDemande.getFullName() != null ? newDemande.getFullName() : existingDemande.getFullName()
            );

            // Save the updated entity
            return demandeRepository.save(existingDemande);
        } else {
            throw new IllegalArgumentException("Demande with ID " + id + " not found.");
        }
    }


    // Delete a Demande
    public void deleteDemande(Long id) {
        if (demandeRepository.existsById(id)) {
            demandeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Demande with ID " + id + " not found.");
        }
    }
}
