package com.immobiler.ProjectGeoInfo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Intermediaire")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intermediaire extends AppUser {

    private String rc;
    private String statut;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "intermediaire",cascade = CascadeType.ALL)
    private UserCom userCom;


}
