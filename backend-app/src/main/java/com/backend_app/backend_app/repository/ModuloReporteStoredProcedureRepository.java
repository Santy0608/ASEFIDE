package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.ModuloReporteDTO;
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
public class ModuloReporteStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ModuloReporteStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarModuloReporte(ModuloReporteDTO moduloReporteDTO) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_MODULO_INSERTAR_SP"); // Nombre del SP

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE", moduloReporteDTO.getNombre());
        inParams.put("P_DESCRIPCION", moduloReporteDTO.getDescripcion());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarModuloReporte(ModuloReporteDTO moduloReporteDTO) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_MODULO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_MODULO", moduloReporteDTO.getIdModulo()) // Identificador clave
                .addValue("P_NOMBRE", moduloReporteDTO.getNombre())
                .addValue("P_DESCRIPCION", moduloReporteDTO.getDescripcion())
                .addValue("P_ID_ESTADO", moduloReporteDTO.getEstadoId());
        jdbcCall.execute(in);
    }

    public void elimminarModuloReporte(ModuloReporteDTO moduloReporteDTO){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_MODULO_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_MODULO", moduloReporteDTO.getIdModulo());
        jdbcCall.execute(in);
    }

}
