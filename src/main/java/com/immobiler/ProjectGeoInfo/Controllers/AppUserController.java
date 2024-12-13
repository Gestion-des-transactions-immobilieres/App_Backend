package com.immobiler.ProjectGeoInfo.Controllers;

import com.immobiler.ProjectGeoInfo.Entities.Citoyen;
import com.immobiler.ProjectGeoInfo.Entities.Intermediaire;
import com.immobiler.ProjectGeoInfo.Services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    // ========== Common Endpoints ========== //

    @GetMapping
    public ResponseEntity<List<?>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return appUserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            appUserService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ========== Citoyen Endpoints ========== //

    @PostMapping("/citoyens")
    public ResponseEntity<Citoyen> createCitoyen(@RequestBody Citoyen citoyen) {
        Citoyen createdCitoyen = appUserService.createCitoyen(citoyen);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCitoyen);
    }

    @PutMapping("/citoyens/{id}")
    public ResponseEntity<Citoyen> updateCitoyen(@PathVariable Long id, @RequestBody Citoyen citoyen) {
        try {
            Citoyen updatedCitoyen = appUserService.updateCitoyen(id, citoyen);
            return ResponseEntity.ok(updatedCitoyen);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ========== Intermediaire Endpoints ========== //

    @PostMapping("/intermediaires")
    public ResponseEntity<Intermediaire> createIntermediaire(@RequestBody Intermediaire intermediaire) {
        Intermediaire createdIntermediaire = appUserService.createIntermediaire(intermediaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIntermediaire);
    }

    // Get Intermediaires by Statut
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Intermediaire>> getIntermediairesByStatut(@PathVariable String statut) {
        List<Intermediaire> intermediaires = appUserService.getIntermediairesByStatut(statut);
        return ResponseEntity.ok(intermediaires);
    }

    @PutMapping("/intermediaires/{id}")
    public ResponseEntity<Intermediaire> updateIntermediaire(@PathVariable Long id, @RequestBody Intermediaire intermediaire) {
        try {
            Intermediaire updatedIntermediaire = appUserService.updateIntermediaire(id, intermediaire);
            return ResponseEntity.ok(updatedIntermediaire);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
