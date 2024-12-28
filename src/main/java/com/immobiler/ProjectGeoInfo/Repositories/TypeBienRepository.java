package com.immobiler.ProjectGeoInfo.Repositories;

import com.immobiler.ProjectGeoInfo.Entities.Statut;
import com.immobiler.ProjectGeoInfo.Entities.TypeBien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeBienRepository extends JpaRepository<TypeBien, Long> {

    Optional<TypeBien> findByNom(String typeBienName);
}
