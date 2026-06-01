package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.dto.TipoTransaccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoAhorroViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoAhorroViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TipoAhorroDTO> getTiposAhorrosCompletos() {
        String sql = "SELECT ID_TIPO_AHORRO, NOMBRE, DESCRIPCION, ESTADO FROM V_LISTAR_FIDE_TIPO_AHORRO_TB ORDER BY ID_TIPO_AHORRO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TipoAhorroDTO dto = new TipoAhorroDTO();
            dto.setIdTipoAhorro(rs.getLong("ID_TIPO_AHORRO"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
