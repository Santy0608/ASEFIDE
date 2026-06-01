package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.DireccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DireccionStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DireccionStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarDireccion(DireccionDTO direccion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_DIRECCION_INSERTAR_SP"); // Nombre del SP

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_PROVINCIA", direccion.getProvincia());
        inParams.put("P_CANTON", direccion.getCanton());
        inParams.put("P_DISTRITO", direccion.getIdDireccion());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarDireccion(DireccionDTO direccion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_DIRECCION_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_DIRECCION", direccion.getIdDireccion()) // Identificador clave
                .addValue("P_PROVINCIA", direccion.getProvincia())
                .addValue("P_CANTON", direccion.getCanton())
                .addValue("P_DISTRITO", direccion.getDistrito())
                .addValue("P_ID_ESTADO", 1);
        jdbcCall.execute(in);
    }

    public void eliminarDireccion(DireccionDTO direccionDTO){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_DIRECCION_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_DIRECCION", direccionDTO.getIdDireccion());
        jdbcCall.execute(in);
    }

}
