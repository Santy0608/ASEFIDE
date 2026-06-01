package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.LugarEventoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LugarEventoViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LugarEventoViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LugarEventoDTO> getLugaresEventosCompletos() {
        String sql = "SELECT ID_LUGAR_EVENTO, NOMBRE_LUGAR, ESTADO FROM V_LISTAR_FIDE_LUGAR_EVENTO_TB ORDER BY ID_LUGAR_EVENTO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LugarEventoDTO dto = new LugarEventoDTO();
            dto.setIdLugarEvento(rs.getLong("ID_LUGAR_EVENTO"));
            dto.setNombreLugar(rs.getString("NOMBRE_LUGAR"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
