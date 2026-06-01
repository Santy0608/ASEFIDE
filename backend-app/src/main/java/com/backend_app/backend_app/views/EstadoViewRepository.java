package com.backend_app.backend_app.views;


import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstadoViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EstadoViewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<EstadoDTO> getEstadosCompletos() {
        String sql = "SELECT ID_ESTADO, NOMBRE FROM V_LISTAR_FIDE_ESTADO_TB ORDER BY ID_ESTADO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            EstadoDTO dto = new EstadoDTO();
            dto.setIdEstado(rs.getLong("ID_ESTADO"));
            dto.setNombre(rs.getString("NOMBRE"));
            return dto;
        });
    }

}
