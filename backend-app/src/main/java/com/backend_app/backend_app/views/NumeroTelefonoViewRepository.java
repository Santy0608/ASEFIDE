package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.DireccionDTO;
import com.backend_app.backend_app.dto.TelefonoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NumeroTelefonoViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NumeroTelefonoViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TelefonoDTO> getTelefonosCompletos() {
        String sql = "SELECT ID_NUMERO, NUMERO_TELEFONO, ESTADO FROM V_LISTAR_FIDE_NUMERO_TELEFONO_TB ORDER BY ID_NUMERO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TelefonoDTO dto = new TelefonoDTO();
            dto.setIdTelefono(rs.getLong("ID_NUMERO"));
            dto.setNumeroTelefono(rs.getString("NUMERO_TELEFONO"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
