package com.immobiler.ProjectGeoInfo.Services;

import com.immobiler.ProjectGeoInfo.Entities.*;
import com.immobiler.ProjectGeoInfo.Repositories.AnnonceRepository;
import com.immobiler.ProjectGeoInfo.Repositories.AppUserRepository;
import com.immobiler.ProjectGeoInfo.Repositories.CitoyenRepository;
import com.immobiler.ProjectGeoInfo.Repositories.CommuneRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;  // <-- Assure-toi d'importer PersistenceContext
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AnnonceService {

    private AnnonceRepository annonceRepository;
    private AppUserRepository appUserRepository;
    private CommuneRepository communeRepository;

    @PersistenceContext  // <-- Annotation nécessaire pour injecter EntityManager
    private EntityManager entityManager;


    // Récupérer une annonce par ID
    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    // Récupérer toutes les annonces
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    // Récupérer les annonces par commune
    public List<Annonce> getAnnonceByCommune(String communeName) {
        Optional<Commune> commune = communeRepository.findByNom(communeName);
        if (commune.isPresent())
        {
            Commune com = (Commune) commune.get();
            return annonceRepository.findByCommune(com);

        } else {
            throw new IllegalArgumentException("commune with ID " + commune + " doesn't contain any annonce");
        }
    }

    // Récupérer les annonces par type de bien
    public List<Annonce> getAnnoncesByType(BienType bienType) {
        return annonceRepository.findByBienType(bienType);
    }

    // Récupérer les annonces par ID du citoyen
    public List<Annonce> getAnnonceByUserId(Long citoyenId) {
        Optional<AppUser> citoyenOptional = appUserRepository.findById(citoyenId);
        if (citoyenOptional.isPresent())
        {
            Citoyen citoyen = (Citoyen) citoyenOptional.get();
            return annonceRepository.findByCitoyen(citoyen);

        } else {
            throw new IllegalArgumentException("Citoyen with ID " + citoyenId + " doesn't have any annonce");
        }
    }

    // Récupérer les annonces par statut
    public List<Annonce> getAnnonceByStatut(String statut) {
        return annonceRepository.findByStatut(statut);
    }

    //    public Annonce addAnnonce(Annonce annonce) {
//        if (annonce.getLocation() != null) {
//
//            GeometryFactory geometryFactory=new GeometryFactory();
//            Point point=geometryFactory.createPoint(new Coordinate(annonce.getLocation().getX(),annonce.getLocation().getY()));
//            // Créer un point géospatial en utilisant ST_Point et ST_SetSRID
////            String sql = "SELECT ST_SetSRID(ST_Point(:x, :y), 4326)";
////            Query query = entityManager.createNativeQuery(sql);
////            query.setParameter("x", annonce.getLocation().getX());
////            query.setParameter("y", annonce.getLocation().getY());
////
////            // Récupérer le point géospatial
////            Object point = query.getSingleResult();
//
//            // Assigner le point géospatial à l'annonce
//            annonce.setLocation( point);
//        }
//
//        // Enregistrer l'annonce avec la location géospatiale
//        return entityManager.merge(annonce);
//    }
//    public Annonce createAnnonce(Annonce annonce) {
//        log.info("saving a new annonce");
//        Annonce savedAnnonce = annonceRepository.save(annonce);
//        return savedAnnonce;
//    }
    public Annonce createAnnonce(Annonce annonce, Long CitoyenId,Long CommuneId, String wkt) {
        Annonce savedAnnonce=new Annonce();
        savedAnnonce.setPrix(annonce.getPrix());
        savedAnnonce.setStatut(annonce.getStatut());
        savedAnnonce.setSurface(annonce.getSurface());
        savedAnnonce.setDescription(annonce.getDescription());
        savedAnnonce.setBienType(annonce.getBienType());
        savedAnnonce.setOperationType(annonce.getOperationType());
        Optional<AppUser> citoyenOptional = appUserRepository.findById(CitoyenId);
        Optional<Commune> commune = communeRepository.findById(CommuneId);
        if (citoyenOptional.isPresent() && commune.isPresent()) {
            Citoyen citoyen = (Citoyen) citoyenOptional.get();
            Commune com=(Commune) commune.get();
            savedAnnonce.setCommune(com);
            savedAnnonce.setCitoyen(citoyen);
            savedAnnonce.setLocation(wkt);
            savedAnnonce.setDateOffre(LocalDate.now());
            return annonceRepository.save(savedAnnonce);
        } else {
            throw new IllegalArgumentException("Citoyen with ID " + CitoyenId + " not found.");
        }
    }



    // Modifier une annonce existante
    public Annonce updateAnnonce(Long id, Annonce newAnnonce) {
        Optional<Annonce> annonceOptional = annonceRepository.findById(id);
        if (annonceOptional.isPresent()) {
            Annonce existingAnnonce = annonceOptional.get();

            // Update attributes only if they are not null
            existingAnnonce.setDescription(
                    newAnnonce.getDescription() != null ? newAnnonce.getDescription() : existingAnnonce.getDescription()
            );
            existingAnnonce.setPrix(
                    newAnnonce.getPrix() != 0 ? newAnnonce.getPrix() : existingAnnonce.getPrix()
            );
            existingAnnonce.setSurface(
                    newAnnonce.getSurface() != 0 ? newAnnonce.getSurface() : existingAnnonce.getSurface()
            );
            existingAnnonce.setStatut(
                    newAnnonce.getStatut() != null ? newAnnonce.getStatut() : existingAnnonce.getStatut()
            );
            existingAnnonce.setBienType(
                    newAnnonce.getBienType() != null ? newAnnonce.getBienType() : existingAnnonce.getBienType()
            );
            existingAnnonce.setOperationType(
                    newAnnonce.getOperationType() != null ? newAnnonce.getOperationType() : existingAnnonce.getOperationType()
            );
            existingAnnonce.setLocation(
                    newAnnonce.getLocation() != null ? newAnnonce.getLocation() : existingAnnonce.getLocation()
            );

            // Save and return the updated entity
            return annonceRepository.save(existingAnnonce);
        } else {
            throw new IllegalArgumentException("Annonce with ID " + id + " not found.");
        }
    }


    // Supprimer une annonce
    @Transactional
    public void deleteAnnonce(Long id) {
        Optional<Annonce> annonceOptional = annonceRepository.findById(id);
        if (annonceOptional.isPresent()) {
            Annonce existingAnnonce = annonceOptional.get();

            // Clear associations
            existingAnnonce.getDemandes().clear();
            existingAnnonce.getJustificatif().clear();
            existingAnnonce.getPhotos().clear();

            annonceRepository.save(existingAnnonce); // Save the cleared entity
            annonceRepository.delete(existingAnnonce); // Delete the entity
        } else {
            throw new IllegalArgumentException("Annonce with ID " + id + " not found.");
        }
    }


}