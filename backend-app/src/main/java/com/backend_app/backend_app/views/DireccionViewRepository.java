package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.DireccionDTO;
import com.backend_app.backend_app.dto.RolDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DireccionViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DireccionViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DireccionDTO> getDireccionesCompletas() {
        String sql = "SELECT ID_DIRECCION, PROVINCIA, CANTON, DISTRITO, ESTADO FROM V_LISTAR_FIDE_DIRECCION_TB ORDER BY ID_DIRECCION DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DireccionDTO dto = new DireccionDTO();
            dto.setIdDireccion(rs.getLong("ID_DIRECCION"));
            dto.setProvincia(rs.getString("PROVINCIA"));
            dto.setCanton(rs.getString("CANTON"));
            dto.setDistrito(rs.getString("DISTRITO"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
