package com.immobiler.ProjectGeoInfo.Services;

import com.immobiler.ProjectGeoInfo.Entities.*;
import com.immobiler.ProjectGeoInfo.Repositories.AppUserRepository;
import com.immobiler.ProjectGeoInfo.Repositories.CitoyenRepository;
import com.immobiler.ProjectGeoInfo.Repositories.IntermediaireRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final CitoyenRepository citoyenRepository;
    private final IntermediaireRepository intermediaireRepository;

    // ================= Common Methods ================= //

    // Get all users
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    // Get a user by ID
    public Optional<AppUser> getUserById(Long id) {
        return appUserRepository.findById(id);
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        if (appUserRepository.existsById(id)) {
            appUserRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }
    }

    // ================= Citoyen Methods ================= //

    // Create a new Citoyen
    public Citoyen createCitoyen(Citoyen citoyen) {
        return citoyenRepository.save(citoyen);
    }

    // Update a Citoyen
    public Citoyen updateCitoyen(Long id, Citoyen newCitoyen) {
        Optional<Citoyen> citoyenOptional = citoyenRepository.findById(id);
        if (citoyenOptional.isPresent()) {
            Citoyen existingCitoyen = citoyenOptional.get();
            updateCommonFields(existingCitoyen, newCitoyen);
            return citoyenRepository.save(existingCitoyen);
        } else {
            throw new IllegalArgumentException("Citoyen with ID " + id + " not found.");
        }
    }

    // ================= Intermediaire Methods ================= //

    // Create a new Intermediaire
    public Intermediaire createIntermediaire(Intermediaire intermediaire) {
        return intermediaireRepository.save(intermediaire);
    }

    // Get Intermediaires by Statut
    public List<Intermediaire> getIntermediairesByStatut(String statut) {
        return intermediaireRepository.findByStatut(statut);
    }

    // Update an Intermediaire
    public Intermediaire updateIntermediaire(Long id, Intermediaire newIntermediaire) {
        Optional<Intermediaire> intermediaireOptional = intermediaireRepository.findById(id);
        if (intermediaireOptional.isPresent()) {
            Intermediaire existingIntermediaire = intermediaireOptional.get();
            updateCommonFields(existingIntermediaire, newIntermediaire);
            existingIntermediaire.setRc(
                    newIntermediaire.getRc() != null ? newIntermediaire.getRc() : existingIntermediaire.getRc()
            );
            existingIntermediaire.setStatut(
                    newIntermediaire.getStatut() != null ? newIntermediaire.getStatut() : existingIntermediaire.getStatut()
            );
            return intermediaireRepository.save(existingIntermediaire);
        } else {
            throw new IllegalArgumentException("Intermediaire with ID " + id + " not found.");
        }
    }

    // ================= Utility Methods ================= //

    // Helper method to update common fields
    private void updateCommonFields(AppUser existingUser, AppUser newUser) {
        existingUser.setLogin(newUser.getLogin() != null ? newUser.getLogin() : existingUser.getLogin());
        existingUser.setPassword(newUser.getPassword() != null ? newUser.getPassword() : existingUser.getPassword());
        existingUser.setEmail(newUser.getEmail() != null ? newUser.getEmail() : existingUser.getEmail());
        existingUser.setTelephone(newUser.getTelephone() != null ? newUser.getTelephone() : existingUser.getTelephone());
        existingUser.setAdresse(newUser.getAdresse() != null ? newUser.getAdresse() : existingUser.getAdresse());
    }
}
