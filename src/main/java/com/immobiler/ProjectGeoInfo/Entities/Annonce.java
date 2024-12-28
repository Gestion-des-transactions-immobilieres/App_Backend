package com.immobiler.ProjectGeoInfo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
public class Annonce implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double surface;
    private double prix;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate dateOffre;

    @Column(columnDefinition = "geometry(Point, 4326)")
    @NotNull
    private Point location;


    //JsonProperty.Access.WRITE_ONLY : to avoid unnecessary serialization.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "annonce", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demande> demandes;

    @OneToMany(mappedBy = "annonce", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;

    @OneToMany(mappedBy = "annonce",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PiecesJustif> justificatif;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(nullable = false)
    private Citoyen citoyen;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(nullable = false)
    private Intermediaire intermediaire;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(nullable = false)
    private Commune commune;

    @ManyToOne
    private TypeBien typeBien;

    @ManyToOne
    private TypeOperation typeOperation;

    @ManyToOne
    private Statut statut;

//    @Enumerated(EnumType.STRING) // Use the OperationType enum here
//    private BienType bienType;
//
//
//    @Enumerated(EnumType.STRING) // Use the OperationType enum here
//    private OperationType operationType;

    @JsonProperty("location")
    public String getLocation() {
        return location != null ? new WKTWriter().write(location) : null;
    }


    public void setLocation(String WKT)  {
        try {
            this.location = (Point) new WKTReader().read(WKT);
        }catch (Exception e){
            new Exception("not valid wkt");
        }
    }
}
