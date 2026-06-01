package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.ResultadosReporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Types;

@Repository
public class ResultadosReporteStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ResultadosReporteStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registrarResultadoReporte(ResultadosReporteDTO res) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_RESULTADOS_REPORTE_INSERTAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_REPORTE", Types.NUMERIC),
                        new SqlParameter("P_METRICA_NOMBRE", Types.VARCHAR),
                        new SqlParameter("P_METRICA_VALOR", Types.NUMERIC)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_REPORTE", res.getReporteId())
                .addValue("P_METRICA_NOMBRE", res.getMetricaNombre())
                .addValue("P_METRICA_VALOR", res.getMetricaValor());

        jdbcCall.execute(in);
    }

    public void editarResultadoReporte(ResultadosReporteDTO res) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_RESULTADOS_REPORTE_EDITAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_RESULTADO", Types.NUMERIC),
                        new SqlParameter("P_ID_REPORTE", Types.NUMERIC),
                        new SqlParameter("P_METRICA_NOMBRE", Types.VARCHAR),
                        new SqlParameter("P_METRICA_VALOR", Types.NUMERIC)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_RESULTADO", res.getIdResultado())
                .addValue("P_ID_REPORTE", res.getReporteId())
                .addValue("P_METRICA_NOMBRE", res.getMetricaNombre())
                .addValue("P_METRICA_VALOR", res.getMetricaValor());

        jdbcCall.execute(in);
    }

}
