package com.immobiler.ProjectGeoInfo.Controllers;

import com.immobiler.ProjectGeoInfo.Entities.Demande;
import com.immobiler.ProjectGeoInfo.Services.DemandeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@AllArgsConstructor
public class DemandeController {

    private DemandeService demandeService;

    // Create a Demande
    @PostMapping
    public ResponseEntity<Demande> createDemande(
            @RequestBody Demande demande,
            @RequestParam Long annonceId,
            @RequestParam Long citoyenId) {
        Demande createdDemande = demandeService.createDemande(demande, annonceId, citoyenId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDemande);
    }

    // Get Demandes by Annonce ID
    @GetMapping("/annonce/{annonceId}")
    public ResponseEntity<List<Demande>> getDemandesByAnnonceId(@PathVariable Long annonceId) {
        List<Demande> demandes = demandeService.getDemandesByAnnonceId(annonceId);
        return ResponseEntity.ok(demandes);
    }

    // Get Demandes by Citoyen ID
    @GetMapping("/citoyen/{citoyenId}")
    public ResponseEntity<List<Demande>> getDemandesByCitoyenId(@PathVariable Long citoyenId) {
        List<Demande> demandes = demandeService.getDemandesByCitoyenId(citoyenId);
        return ResponseEntity.ok(demandes);
    }

    // Update a Demande
    @PutMapping("/{id}")
    public ResponseEntity<Demande> updateDemande(@PathVariable Long id, @RequestBody Demande demande) {
        Demande updatedDemande = demandeService.updateDemande(id, demande);
        return ResponseEntity.ok(updatedDemande);
    }

    // Delete a Demande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
        return ResponseEntity.noContent().build();
    }
}
