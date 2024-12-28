package com.immobiler.ProjectGeoInfo.Repositories;

import com.immobiler.ProjectGeoInfo.Entities.TypeOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeOperationRepository extends JpaRepository<TypeOperation, Long> {
    Optional<TypeOperation> findByNom(String typeOperationName);
}
