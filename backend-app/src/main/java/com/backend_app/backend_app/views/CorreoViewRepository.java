package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.TelefonoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CorreoViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CorreoViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CorreoDTO> getCorreosCompletos() {
        String sql = "SELECT ID_CORREO, CORREO_ELECTRONICO, ESTADO FROM V_LISTAR_FIDE_CORREO_ELECTRONICO_TB ORDER BY ID_CORREO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CorreoDTO dto = new CorreoDTO();
            dto.setIdCorreo(rs.getLong("ID_CORREO"));
            dto.setCorreoElectronico(rs.getString("CORREO_ELECTRONICO"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }


}
