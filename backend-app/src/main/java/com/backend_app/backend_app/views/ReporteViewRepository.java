package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.ReporteDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReporteViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReporteViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReporteDTO> getReportesCompletos() {
        String sql = "SELECT ID_REPORTE, TIPO_REPORTE, FECHA_INICIO, FECHA_FINAL, MODULO, TOTAL_REGISTROS, RESUMEN_MONTOS, FECHA_GENERACION, ESTADO, GENERADO_POR FROM V_LISTAR_FIDE_REPORTES_TB ORDER BY ID_REPORTE DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReporteDTO dto = new ReporteDTO();
            dto.setIdReporte(rs.getLong("ID_REPORTE"));
            dto.setNombreTipoReporte(rs.getString("TIPO_REPORTE"));
            dto.setFechaInicio(rs.getDate("FECHA_INICIO"));
            dto.setFechaFinal(rs.getDate("FECHA_FINAL"));
            dto.setFechaGeneracion(rs.getDate("FECHA_GENERACION"));
            dto.setNombreModuloReporte(rs.getString("MODULO"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            dto.setNombreUsuario(rs.getString("GENERADO_POR"));
            return dto;
        });
    }

}
