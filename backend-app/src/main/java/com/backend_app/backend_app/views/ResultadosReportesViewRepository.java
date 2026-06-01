package com.backend_app.backend_app.views;


import com.backend_app.backend_app.dto.ReporteDTO;
import com.backend_app.backend_app.dto.ResultadosReporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResultadosReportesViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResultadosReportesViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ResultadosReporteDTO> getResultadosReportesCompletos() {
        String sql = "SELECT ID_RESULTADO, ID_REPORTE, TIPO_REPORTE, METRICA_NOMBRE, METRICA_VALOR FROM V_LISTAR_FIDE_RESULTADOS_REPORTE_TB ORDER BY ID_RESULTADO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ResultadosReporteDTO dto = new ResultadosReporteDTO();
            dto.setIdResultado(rs.getLong("ID_RESULTADO"));
            dto.setReporteId(rs.getLong("ID_REPORTE"));
            dto.setTipoReporte(rs.getString("TIPO_REPORTE"));
            dto.setMetricaNombre(rs.getString("METRICA_NOMBRE"));
            dto.setMetricaValor(rs.getString("METRICA_VALOR"));
            return dto;
        });
    }

}
