package com.immobiler.ProjectGeoInfo.Controllers;

import com.immobiler.ProjectGeoInfo.Entities.Demande;
import com.immobiler.ProjectGeoInfo.Entities.Photo;
import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Repositories.AnnonceRepository;
import com.immobiler.ProjectGeoInfo.Services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private AnnonceRepository annonceRepository;

    // Endpoint pour récupérer les photos par l'ID de l'annonce
    @GetMapping("/annonce/{annonceId}")
    public ResponseEntity<List<Photo>> getPhotosByAnnonceId(@PathVariable Long annonceId) {
        List<Photo> photos = photoService.getPhotosByAnnonceId(annonceId);
        return ResponseEntity.ok(photos);
    }

    // Endpoint pour uploader un fichier
    @PostMapping("/upload")
    public ResponseEntity<Photo> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("annonceId") Long annonceId) throws IOException {

        // Récupérer l'annonce à partir de son ID
        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new IllegalArgumentException("Annonce not found with ID " + annonceId));

        // Appeler le service pour gérer le téléchargement du fichier
        Photo photo = photoService.uploadFile(file, annonce);

        // Retourner la photo avec l'ID et le chemin du fichier
        return ResponseEntity.ok(photo);
    }
}
