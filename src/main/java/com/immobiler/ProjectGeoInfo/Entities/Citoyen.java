package com.immobiler.ProjectGeoInfo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("Citoyen")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Citoyen extends AppUser{


    @OneToMany(mappedBy = "citoyen",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Annonce> annonces;

    @OneToMany(mappedBy = "citoyen",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demande> demandes;

    public Citoyen(Long id,String name, String login, String password, String email, String telephone, String adresse, List<Annonce> annonces, List<Demande> demandes) {
        super(id, name,login, password, email, telephone, adresse); // Appel du constructeur parent AppUser
        this.annonces = annonces;
        this.demandes = demandes;
    }
}
