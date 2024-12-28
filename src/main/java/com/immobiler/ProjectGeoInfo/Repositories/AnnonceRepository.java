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
    List<Annonce> findByTypeBien(TypeBien typeBien);
    List<Annonce> findByTypeOperation(TypeOperation typeOperation);
    List<Annonce> findByStatut(Statut statut);


    // Récupérer les annonces par ID du citoyen (propriétaire de l'annonce)
    List<Annonce> findByCitoyen(Citoyen citoyen);


}
