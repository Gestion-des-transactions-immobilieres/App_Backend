package com.immobiler.ProjectGeoInfo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOperation {
        public TypeOperation(Long id, String nom) {
                this.id = id;
                this.nom = nom;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String nom; // Exemple : Vente, Location

        @OneToMany(mappedBy = "typeOperation",fetch = FetchType.LAZY)
        private List<Annonce> annonces;
    }
