package com.immobiler.ProjectGeoInfo.Repositories;

import com.immobiler.ProjectGeoInfo.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByLogin(String login);// Exemple de méthode pour trouver un utilisateur par son login
    Optional<AppUser> findById(Long id);
}
