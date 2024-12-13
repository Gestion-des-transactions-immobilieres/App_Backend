package com.immobiler.ProjectGeoInfo.dtos;

import com.immobiler.ProjectGeoInfo.Entities.Annonce;

public class AnnonceRequestDTO {
    private Annonce annonce;
    private Long citoyenId;
    private Long communeId;
    private String wkt;

    // Getters and setters
    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public Long getCitoyenId() {
        return citoyenId;
    }

    public void setCitoyenId(Long citoyenId) {
        this.citoyenId = citoyenId;
    }

    public Long getCommuneId() {
        return communeId;
    }

    public void setCommuneId(Long communeId) {
        this.communeId = communeId;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }
}
