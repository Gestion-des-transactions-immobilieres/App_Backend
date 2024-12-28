package com.immobiler.ProjectGeoInfo.Config;

import com.immobiler.ProjectGeoInfo.Entities.*;
import com.immobiler.ProjectGeoInfo.Repositories.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TypeOperationRepository typeOperationRepository;
    private final TypeBienRepository typeBienRepository;
    private final CommuneRepository communeRepository;
    private final CitoyenRepository citoyenRepository;
    private final AnnonceRepository annonceRepository;
    private final StatutRepository statutRepository;
    private final IntermediaireRepository intermediaireRepository;

    public DataInitializer(TypeOperationRepository typeOperationRepository,
                           TypeBienRepository typeBienRepository,
                           CommuneRepository communeRepository,
                           CitoyenRepository citoyenRepository,
                           AnnonceRepository annonceRepository,
                           StatutRepository statutRepository,
                           IntermediaireRepository intermediaireRepository) {
        this.typeOperationRepository = typeOperationRepository;
        this.typeBienRepository = typeBienRepository;
        this.communeRepository = communeRepository;
        this.citoyenRepository = citoyenRepository;
        this.annonceRepository = annonceRepository;
        this.statutRepository = statutRepository;
        this.intermediaireRepository=intermediaireRepository;
    }

    @Override
    public void run(String... args) {
        // Insérer des types d'opérations
        TypeOperation vente = new TypeOperation(null, "Vente");
        TypeOperation location = new TypeOperation(null, "Location");
        typeOperationRepository.save(vente);
        typeOperationRepository.save(location);

        // Insérer des types de biens
        TypeBien appartement = new TypeBien(null, "Appartement");
        TypeBien villa = new TypeBien(null, "Villa");
        typeBienRepository.save(appartement);
        typeBienRepository.save(villa);

        // Insérer des statuts
        Statut disponible = new Statut(null, "Disponible");
        Statut reserve = new Statut(null, "Réservé");
        statutRepository.save(disponible);
        statutRepository.save(reserve);

        // Insérer des communes
        Commune casablanca = new Commune(null, "Casablanca", null, null, null);
        Commune rabat = new Commune(null, "Rabat", null, null, null);
        communeRepository.save(casablanca);
        communeRepository.save(rabat);

        // Insérer un citoyen
        Citoyen citoyen = new Citoyen(null,"Ahmed", "citoyen1", "password123", "email1@example.com", "0612345678", "Adresse 1", null, null);
        citoyenRepository.save(citoyen);

        Intermediaire intermediaire = new Intermediaire();
        intermediaire.setName("Default Intermediaire");
        // Save the intermediaire if needed (assume an IntermediaireRepository exists)
        intermediaire = intermediaireRepository.save(intermediaire);


        // Ajouter une annonce (initialisation)
        Annonce annonce = new Annonce();
        annonce.setSurface(120.0);
        annonce.setPrix(350000.0);
        annonce.setDescription("Belle villa à vendre");
        annonce.setDateOffre(LocalDate.now());
        annonce.setStatut(disponible);

        // Ajouter une localisation (Point) - Exemple pour un endroit fictif (Longitude, Latitude)
        try {
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coordinate = new Coordinate(-7.6296, 33.5896);  // Replace with valid coordinates
            Point point = geometryFactory.createPoint(coordinate);

            // Convert Point to WKT string
            String wkt = new WKTWriter().write(point);
            annonce.setLocation(wkt);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du point de localisation : " + e.getMessage());
        }


        // Lier les entités associées
        annonce.setIntermediaire(intermediaire);
        annonce.setCitoyen(citoyen);
        annonce.setCommune(casablanca);
        annonce.setTypeBien(villa);
        annonce.setTypeOperation(vente);

        // Sauvegarder l'annonce dans la base de données
        annonceRepository.save(annonce);

        System.out.println("Annonce initialisée et enregistrée avec succès !");
    }
}
