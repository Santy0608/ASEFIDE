package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.ModuloReporteDTO;
import com.backend_app.backend_app.dto.TipoReporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoReporteViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoReporteViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TipoReporteDTO> getTiposReportesCompletos() {
        String sql = "SELECT ID_TIPO_REPORTE, NOMBRE, ESTADO FROM V_LISTAR_FIDE_TIPO_REPORTE_TB ORDER BY ID_TIPO_REPORTE DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TipoReporteDTO dto = new TipoReporteDTO();
            dto.setIdTipoReporte(rs.getLong("ID_TIPO_REPORTE"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
