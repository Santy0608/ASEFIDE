package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.dto.TipoReporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TipoReporteStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoReporteStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarTipoReporte(TipoReporteDTO tipoReporteDTO) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_REPORTE_INSERTAR_SP"); // Nombre del SP

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE", tipoReporteDTO.getNombre());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarTipoReporte(TipoReporteDTO tipoReporteDTO) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_REPORTE_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TIPO_REPORTE", tipoReporteDTO.getIdTipoReporte()) // Identificador clave
                .addValue("P_NOMBRE", tipoReporteDTO.getNombre())
                .addValue("P_ID_ESTADO", 1);
        jdbcCall.execute(in);
    }

    public void eliminarTipoReporte(TipoReporteDTO tipoReporteDTO){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_REPORTE_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TIPO_REPORTE", tipoReporteDTO.getIdTipoReporte());
        jdbcCall.execute(in);
    }

}
