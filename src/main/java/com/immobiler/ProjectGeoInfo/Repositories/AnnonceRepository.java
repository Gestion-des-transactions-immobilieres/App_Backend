package com.immobiler.ProjectGeoInfo.Repositories;

import com.immobiler.ProjectGeoInfo.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {



    // Récupérer les annonces par commune
    List<Annonce> findByCommune(Commune commune);

    // Récupérer les annonces par type de bien
    List<Annonce> findByBienType(BienType bienType);

    // Récupérer les annonces par type d'opération (location ou vente)
    List<Annonce> findByOperationType(OperationType operationType);

    // Récupérer les annonces par ID du citoyen (propriétaire de l'annonce)
    List<Annonce> findByCitoyen(Citoyen citoyen);

    // Récupérer les annonces par statut (disponible, réservé, etc.)
    List<Annonce> findByStatut(String statut);
}
