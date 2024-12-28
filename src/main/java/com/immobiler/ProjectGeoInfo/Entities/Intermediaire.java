package com.immobiler.ProjectGeoInfo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("Intermediaire")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intermediaire extends AppUser {

    private String rc;
    private String statut;

    @OneToMany(mappedBy = "intermediaire",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Annonce> annonces;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "intermediaire",cascade = CascadeType.ALL)
    private UserCom userCom;


}
