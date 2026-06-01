package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.dto.RolDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RolViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RolViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RolDTO> getRolesCompletos() {
        String sql = "SELECT ID_ROL, NOMBRE_ROL, ESTADO FROM V_LISTAR_FIDE_ROL_TB ORDER BY ID_ROL DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RolDTO dto = new RolDTO();
            dto.setIdRol(rs.getLong("ID_ROL"));
            dto.setNombreRol(rs.getString("NOMBRE_ROL"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
