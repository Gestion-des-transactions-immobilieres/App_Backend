package com.immobiler.ProjectGeoInfo.Services;

import com.immobiler.ProjectGeoInfo.Entities.Photo;
import com.immobiler.ProjectGeoInfo.Entities.PiecesJustif;
import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Repositories.AnnonceRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class PiecesJustifService {

    @Value("${file.upload-dir}")  // Utilisation d'une propriété configurable pour le répertoire de stockage
    private String uploadDir;

    @Autowired
    private PiecesJustifRepository piecesJustifRepository;

    private AnnonceRepository annonceRepository;

    public PiecesJustif uploadFile(MultipartFile file, Annonce annonce) throws IOException {
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
        if (!"jpg".equals(extension) && !"png".equals(extension) && !"pdf".equals(extension)) {
            throw new IllegalArgumentException("Seuls les fichiers JPG, PNG et PDF sont autorisés.");
        }

        // Sauvegarder le fichier dans le répertoire
        Files.write(path, file.getBytes());

        // Créer l'entité PiecesJustif et l'associer à l'annonce
        PiecesJustif piecesJustif = new PiecesJustif();
        piecesJustif.setName(originalFileName);  // Nom original du fichier
        piecesJustif.setFilePath(path.toString());  // Chemin absolu du fichier
        piecesJustif.setAnnonce(annonce);  // Associer l'annonce

        // Sauvegarder l'objet PiecesJustif dans la base de données
        return piecesJustifRepository.save(piecesJustif);
    }

    // Fetch piecesJustif by Annonce ID
    public List<PiecesJustif> getpiecesJustifByAnnonceId(Long annonceId) {
        Optional<Annonce> annonceOptional = annonceRepository.findById(annonceId);
        if (annonceOptional.isPresent()) {
            return piecesJustifRepository.findByAnnonce(annonceOptional.get());
        } else {
            throw new IllegalArgumentException("Annonce with ID " + annonceId + " not found.");
        }
    }
}
