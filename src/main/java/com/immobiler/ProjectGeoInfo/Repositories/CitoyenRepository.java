package com.immobiler.ProjectGeoInfo.Repositories;


import com.immobiler.ProjectGeoInfo.Entities.Citoyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitoyenRepository extends JpaRepository<Citoyen, Long> {
}
