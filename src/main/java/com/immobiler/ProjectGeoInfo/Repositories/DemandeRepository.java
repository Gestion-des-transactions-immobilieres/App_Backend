package com.immobiler.ProjectGeoInfo.Repositories;

import com.immobiler.ProjectGeoInfo.Entities.Annonce;
import com.immobiler.ProjectGeoInfo.Entities.Citoyen;
import com.immobiler.ProjectGeoInfo.Entities.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {

    // Récupérer toutes les demandes par l'ID de l'annonce
    List<Demande> findByAnnonce(Annonce annonce);

    // Récupérer toutes les demandes par l'ID du citoyen
    List<Demande> findByCitoyen(Citoyen citoyen);
}
