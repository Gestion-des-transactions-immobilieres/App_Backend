package com.immobiler.ProjectGeoInfo.Services;

import com.immobiler.ProjectGeoInfo.Entities.Photo;
import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Repositories.AnnonceRepository;
import com.immobiler.ProjectGeoInfo.Repositories.PhotoRepository;
import lombok.AllArgsConstructor;
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

public class PhotoService {

    @Value("${images.upload-dir}")  // Directory to store uploaded images
    private String uploadDir;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AnnonceRepository annonceRepository;

    public Photo uploadFile(MultipartFile file, Annonce annonce) throws IOException {
        // Ensure the upload directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique file name
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // Validate the file extension
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        if (!"jpg".equals(extension) && !"png".equals(extension)) {
            throw new IllegalArgumentException("Only JPG and PNG files are allowed.");
        }

        // Save the file temporarily to validate its MIME type
        Path tempFilePath = Paths.get(uploadDir, uniqueFileName);
        Files.write(tempFilePath, file.getBytes());

        // Validate MIME type using the saved file
        String mimeType = Files.probeContentType(tempFilePath);
        if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
            // Delete the invalid file before throwing an exception
            Files.delete(tempFilePath);
            throw new IllegalArgumentException("Invalid file type. Only JPG and PNG files are supported.");
        }

        // If MIME type is valid, return the saved photo entity
        Photo photo = new Photo();
        photo.setImagePath("/uploads/" + uniqueFileName); // Relative path for the image
        photo.setAnnonce(annonce);
        return photoRepository.save(photo);
    }

    // Fetch photos by Annonce ID
    public List<Photo> getPhotosByAnnonceId(Long annonceId) {
        Optional<Annonce> annonceOptional = annonceRepository.findById(annonceId);
        if (annonceOptional.isPresent()) {
            return photoRepository.findByAnnonce(annonceOptional.get());
        } else {
            throw new IllegalArgumentException("Annonce with ID " + annonceId + " not found.");
        }
    }
}
