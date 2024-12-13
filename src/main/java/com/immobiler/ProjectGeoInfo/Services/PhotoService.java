package com.immobiler.ProjectGeoInfo.Services;

import com.immobiler.ProjectGeoInfo.Entities.Demande;
import com.immobiler.ProjectGeoInfo.Entities.Photo;
import com.immobiler.ProjectGeoInfo.Entities.PiecesJustif;
import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Repositories.PhotoRepository;
import com.immobiler.ProjectGeoInfo.Repositories.PiecesJustifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {

    @Value("${images.upload-dir}")  // Utilisation d'une propriété configurable pour le répertoire de stockage
    private String uploadDir;

    @Autowired
    private PhotoRepository photoRepository;

    public Photo uploadFile(MultipartFile file, Annonce annonce) throws IOException {
        // Créer le répertoire si il n'existe pas
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Générer un nom de fichier unique pour éviter les collisions
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // Créer le chemin absolu du fichier
        Path path = Paths.get(uploadDir, uniqueFileName);

        // Vérifier l'extension du fichier (facultatif, mais recommandé pour la sécurité)
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        if (!"jpg".equals(extension) && !"png".equals(extension)) {
            throw new IllegalArgumentException("Seuls les fichiers JPG, PNG  sont autorisés.");
        }

        // Sauvegarder le fichier dans le répertoire
        Files.write(path, file.getBytes());

        // Créer l'entité photo et l'associer à l'annonce
        Photo photo = new Photo();
        photo.setImagePath(path.toString());  // Chemin absolu du fichier
        photo.setAnnonce(annonce);  // Associer l'annonce

        // Sauvegarder l'objet PiecesJustif dans la base de données
        return photoRepository.save(photo);
    }

    // Récupérer les demandes par l'ID de l'annonce
    public List<Photo> getPhotosByAnnonceId(Long annonceId) {
        return photoRepository.findByAnnonceId(annonceId);
    }
}
