package com.immobiler.ProjectGeoInfo.Controllers;

import com.immobiler.ProjectGeoInfo.Services.AnnonceService;
import com.immobiler.ProjectGeoInfo.dtos.AnnonceRequestDTO;
import com.immobiler.ProjectGeoInfo.dtos.AnnonceResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annonces")
@AllArgsConstructor
public class AnnonceController {

    private final AnnonceService annonceService;

    // Récupérer une annonce par ID
    @GetMapping("/{id}")
    public ResponseEntity<AnnonceResponseDTO> getAnnonceById(@PathVariable Long id) {
        return ResponseEntity.ok(annonceService.getAnnonceById(id));
    }

    // Récupérer toutes les annonces
    @GetMapping
    public ResponseEntity<List<AnnonceResponseDTO>> getAllAnnonces() {
        return ResponseEntity.ok(annonceService.getAllAnnonces());
    }

    // Récupérer les annonces par commune
    @GetMapping("/commune/{communeName}")
    public ResponseEntity<List<AnnonceResponseDTO>> getAnnoncesByCommune(@PathVariable String communeName) {
        return ResponseEntity.ok(annonceService.getAnnonceByCommune(communeName));
    }

    // Récupérer les annonces par type de bien
    @GetMapping("/type-bien/{typeBienName}")
    public ResponseEntity<List<AnnonceResponseDTO>> getAnnoncesByTypeBien(@PathVariable String typeBienName) {
        return ResponseEntity.ok(annonceService.getAnnoncesByTypeBien(typeBienName));
    }

    // Récupérer les annonces par type d'opération
    @GetMapping("/type-operation/{typeOperationName}")
    public ResponseEntity<List<AnnonceResponseDTO>> getAnnoncesByTypeOperation(@PathVariable String typeOperationName) {
        return ResponseEntity.ok(annonceService.getAnnoncesByTypeOperation(typeOperationName));
    }

    // Récupérer les annonces par ID du citoyen
    @GetMapping("/citoyen/{citoyenId}")
    public ResponseEntity<List<AnnonceResponseDTO>> getAnnoncesByUserId(@PathVariable Long citoyenId) {
        return ResponseEntity.ok(annonceService.getAnnonceByUserId(citoyenId));
    }

    // Récupérer les annonces par statut
    @GetMapping("/statut/{statutName}")
    public ResponseEntity<List<AnnonceResponseDTO>> getAnnoncesByStatut(@PathVariable String statutName) {
        return ResponseEntity.ok(annonceService.getAnnonceByStatut(statutName));
    }

    // Créer une annonce
    @PostMapping
    public ResponseEntity<AnnonceResponseDTO> createAnnonce(@RequestBody AnnonceRequestDTO annonceRequestDTO) {
        AnnonceResponseDTO createdAnnonce = annonceService.createAnnonce(annonceRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnonce);
    }

    // Modifier une annonce existante
    @PutMapping("/{id}")
    public ResponseEntity<AnnonceResponseDTO> updateAnnonce(@PathVariable Long id, @RequestBody AnnonceRequestDTO annonceRequestDTO) {
        AnnonceResponseDTO updatedAnnonce = annonceService.updateAnnonce(id, annonceRequestDTO);
        return ResponseEntity.ok(updatedAnnonce);
    }

    // Supprimer une annonce
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteAnnonce(id);
        return ResponseEntity.noContent().build();
    }
}
