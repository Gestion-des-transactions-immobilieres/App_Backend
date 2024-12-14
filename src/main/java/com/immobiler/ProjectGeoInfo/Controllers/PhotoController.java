package com.immobiler.ProjectGeoInfo.Controllers;

import com.immobiler.ProjectGeoInfo.Entities.Photo;
import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Repositories.AnnonceRepository;
import com.immobiler.ProjectGeoInfo.Services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Fetch photos by Annonce ID
    @GetMapping("/annonce/{annonceId}")
    public ResponseEntity<List<Photo>> getPhotosByAnnonceId(@PathVariable Long annonceId) {
        List<Photo> photos = photoService.getPhotosByAnnonceId(annonceId);
        return ResponseEntity.ok(photos);
    }

    // Upload a photo for an Annonce
    @PostMapping("/upload")
    public ResponseEntity<Photo> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("annonceId") Long annonceId) throws IOException {

        // Fetch the associated Annonce
        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new IllegalArgumentException("Annonce not found with ID " + annonceId));

        // Upload the photo
        Photo photo = photoService.uploadFile(file, annonce);

        return ResponseEntity.status(HttpStatus.CREATED).body(photo);
    }
}
