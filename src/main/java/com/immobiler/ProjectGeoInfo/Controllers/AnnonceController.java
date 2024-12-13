package com.immobiler.ProjectGeoInfo.Controllers;

import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Entities.BienType;
import com.immobiler.ProjectGeoInfo.Entities.Demande;
import com.immobiler.ProjectGeoInfo.Services.AnnonceService;
import com.immobiler.ProjectGeoInfo.Utilitaires.PointConverter;
import com.immobiler.ProjectGeoInfo.dtos.AnnonceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.reflect.Array.setDouble;

@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {
    @Autowired
    private AnnonceService annonceService;

    // Ajouter une annonce
    @PostMapping
    public ResponseEntity<Annonce> createAnnonce(@RequestBody AnnonceRequestDTO annonceRequest) {
        Annonce createdAnnonce = annonceService.createAnnonce(
                annonceRequest.getAnnonce(),
                annonceRequest.getCitoyenId(),
                annonceRequest.getCommuneId(),
                annonceRequest.getWkt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnonce);
    }

    // Endpoint pour récupérer les demandes par l'ID du citoyen
    @GetMapping("/citoyen/{citoyenId}")
    public ResponseEntity<List<Annonce>> getAnnoncesByCitoyenId(@PathVariable Long citoyenId) {
        List<Annonce> annonces = annonceService.getAnnonceByUserId(citoyenId);
        return ResponseEntity.ok(annonces);
    }

    // Récupérer les annonces par commune
    @GetMapping("/commune/{communeName}")
    public ResponseEntity<List<Annonce>> getAnnonceByCommune(@PathVariable String communeName) {
        List<Annonce> annonces = annonceService.getAnnonceByCommune(communeName);
        return ResponseEntity.ok(annonces);
    }

    // Récupérer les annonces par type de bien
    @GetMapping("/type/{bienType}")
    public ResponseEntity<List<Annonce>> getAnnoncesByType(@PathVariable BienType bienType) {
        List<Annonce> annonces = annonceService.getAnnoncesByType(bienType);
        return ResponseEntity.ok(annonces);
    }

    // Récupérer les annonces par statut
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Annonce>> getAnnoncesByStatut(@PathVariable String statut) {
        List<Annonce> annonces = annonceService.getAnnonceByStatut(statut);
        return ResponseEntity.ok(annonces);
    }

    // Récupérer une annonce par ID
    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonceById(@PathVariable Long id) {
        return annonceService.getAnnonceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer toutes les annonces
    @GetMapping
    public ResponseEntity<List<Annonce>> getAllAnnonces() {
        return ResponseEntity.ok(annonceService.getAllAnnonces());
    }

    // Modifier une annonce
    @PutMapping("/{id}")
    public ResponseEntity<Annonce> updateAnnonce(@PathVariable Long id, @RequestBody Annonce annonce) {
        try {
            Annonce updatedAnnonce = annonceService.updateAnnonce(id, annonce);
            return ResponseEntity.ok(updatedAnnonce);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    // Supprimer une annonce
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        try {
            annonceService.deleteAnnonce(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
