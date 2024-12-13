package com.immobiler.ProjectGeoInfo.Controllers;

import com.immobiler.ProjectGeoInfo.Entities.PiecesJustif;
import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Services.PiecesJustifService;
import com.immobiler.ProjectGeoInfo.Repositories.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pieces-justificatives")
public class PiecesJustifController {

    @Autowired
    private PiecesJustifService piecesJustifService;

    @Autowired
    private AnnonceRepository annonceRepository;

    // Endpoint pour uploader un fichier
    @PostMapping("/upload")
    public ResponseEntity<PiecesJustif> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("annonceId") Long annonceId) throws IOException {

        // Récupérer l'annonce à partir de son ID
        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new IllegalArgumentException("Annonce not found with ID " + annonceId));

        // Appeler le service pour gérer le téléchargement du fichier
        PiecesJustif piecesJustif = piecesJustifService.uploadFile(file, annonce);

        // Retourner la pièce justificative avec l'ID et le chemin du fichier
        return ResponseEntity.ok(piecesJustif);
    }
}
