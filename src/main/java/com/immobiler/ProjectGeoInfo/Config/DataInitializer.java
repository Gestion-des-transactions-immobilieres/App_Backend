package com.immobiler.ProjectGeoInfo.Config;

import com.immobiler.ProjectGeoInfo.Entities.*;
import com.immobiler.ProjectGeoInfo.Repositories.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TypeOperationRepository typeOperationRepository;
    private final TypeBienRepository typeBienRepository;
    private final CommuneRepository communeRepository;
    private final CitoyenRepository citoyenRepository;
    private final AnnonceRepository annonceRepository;  // Ajout du repository Annonce

    public DataInitializer(TypeOperationRepository typeOperationRepository,
                           TypeBienRepository typeBienRepository,
                           CommuneRepository communeRepository,
                           CitoyenRepository citoyenRepository,
                           AnnonceRepository annonceRepository) {  // Injection de dépendance du repository Annonce
        this.typeOperationRepository = typeOperationRepository;
        this.typeBienRepository = typeBienRepository;
        this.communeRepository = communeRepository;
        this.citoyenRepository = citoyenRepository;
        this.annonceRepository = annonceRepository;  // Initialisation du repository Annonce
    }

    @Override
    public void run(String... args) throws Exception {
        // Insérer des types d'opérations
        TypeOperation vente = new TypeOperation(null, OperationType.VENTE);
        TypeOperation location = new TypeOperation(null, OperationType.LOCATION);
        typeOperationRepository.save(vente);
        typeOperationRepository.save(location);

        // Insérer des types de biens
        TypeBien appartement = new TypeBien(null, BienType.APPARTEMENT);
        TypeBien villa = new TypeBien(null, BienType.VILLA);
        typeBienRepository.save(appartement);
        typeBienRepository.save(villa);

        // Insérer des communes
        Commune casablanca = new Commune(null, "Casablanca", null, null,null);
        Commune rabat = new Commune(null, "Rabat", null, null,null);
        communeRepository.save(casablanca);
        communeRepository.save(rabat);

        // Insérer un citoyen
        Citoyen citoyen = new Citoyen(null, "citoyen1", "password123", "email1@example.com", "0612345678", "Adresse 1", null, null);
        citoyenRepository.save(citoyen);

        // Ajouter une annonce (initialisation)
        Annonce annonce = new Annonce();
        annonce.setSurface(120.0);
        annonce.setPrix(350000.0);
        annonce.setDescription("Belle villa à vendre");
        annonce.setDateOffre(LocalDate.now());
        annonce.setStatut("Disponible");

        // Ajouter une localisation (Point) - Exemple pour un endroit fictif (Longitude, Latitude)
        GeometryFactory geometryFactory = new GeometryFactory();
        //Coordinate coordinate = new Coordinate(-7.6296, 33.5896);  // Remplace avec des coordonnées valides
        //Point point = geometryFactory.createPoint(coordinate);
        String wkt="POINT(-7.6296, 33.5896)";
        annonce.setLocation(wkt);

        // Lier les entités associées
        annonce.setCitoyen(citoyen);
        annonce.setCommune(casablanca);
        annonce.setBienType(BienType.VILLA);
        annonce.setOperationType(OperationType.VENTE);

        // Sauvegarder l'annonce dans la base de données
        annonceRepository.save(annonce);

        System.out.println("Annonce initialisée et enregistrée avec succès !");
    }
}
