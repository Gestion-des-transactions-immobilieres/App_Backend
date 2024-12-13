package com.immobiler.ProjectGeoInfo.Utilitaires;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class PointConverter {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Point fromCoordinates(double x, double y) {
        // Crée le point avec ST_Point dans PostgreSQL via une requête SQL native
        String sql = "SELECT ST_SetSRID(ST_Point(?, ?), 4326)"; // SRID 4326 pour WGS 84
        // Utilisation de JdbcTemplate pour exécuter la requête SQL
        Object point = jdbcTemplate.queryForObject(sql, new Object[]{x, y}, Object.class);

        // Retourner le résultat sous forme de point géospatial
        return (Point) point;
    }
}
