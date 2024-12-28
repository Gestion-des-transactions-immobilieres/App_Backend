package com.immobiler.ProjectGeoInfo.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nom; // Exemple : Disponible, Réservé

    @OneToMany(mappedBy = "statut",fetch = FetchType.LAZY)
    private List<Annonce> annonces;

    public Statut(Long id, String nom) {
        this.id = id;
        this.nom = nom;

    }
}
