package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.ModuloReporteDTO;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuloViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ModuloViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ModuloReporteDTO> getModulosReportesCompletos() {
        String sql = "SELECT ID_MODULO, NOMBRE, DESCRIPCION, ESTADO FROM V_LISTAR_FIDE_MODULO_TB ORDER BY ID_MODULO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ModuloReporteDTO dto = new ModuloReporteDTO();
            dto.setIdModulo(rs.getLong("ID_MODULO"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
