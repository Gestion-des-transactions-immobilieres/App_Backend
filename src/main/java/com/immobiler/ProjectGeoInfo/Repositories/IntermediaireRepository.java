package com.immobiler.ProjectGeoInfo.Repositories;

import com.immobiler.ProjectGeoInfo.Entities.Intermediaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntermediaireRepository extends JpaRepository<Intermediaire, Long> {
    List<Intermediaire> findByStatut(String statut);

}
