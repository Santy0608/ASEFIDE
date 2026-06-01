package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.domain.Estado;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.EstadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EstadoStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EstadoStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarEstado(EstadoDTO estado) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_ESTADO_INSERTAR_SP"); // Nombre del SP

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE", estado.getNombre());
        jdbcCall.execute(inParams);
    }


    public void editarEstado(EstadoDTO estado) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_ESTADO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_ESTADO", estado.getIdEstado()) // Identificador clave
                .addValue("P_NOMBRE", estado.getNombre());
        jdbcCall.execute(in);
    }


}
