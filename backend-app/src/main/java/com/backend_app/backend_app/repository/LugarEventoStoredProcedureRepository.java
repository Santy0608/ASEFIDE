package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.LugarEventoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LugarEventoStoredProcedureRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LugarEventoStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarLugarEvento(LugarEventoDTO lugarEvento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_LUGAR_EVENTO_INSERTAR_SP"); // Nombre del SP

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE_LUGAR", lugarEvento.getNombreLugar());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarLugarEvento(LugarEventoDTO lugarEvento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_LUGAR_EVENTO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_LUGAR_EVENTO", lugarEvento.getIdLugarEvento()) // Identificador clave
                .addValue("P_NOMBRE_LUGAR", lugarEvento.getNombreLugar())
                .addValue("P_ID_ESTADO", 1);
        jdbcCall.execute(in);
    }

    public void eliminarLugarEvento(LugarEventoDTO lugarEvento){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_LUGAR_EVENTO_ELIMINAR_SP");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_LUGAR_EVENTO", lugarEvento.getIdLugarEvento());
        jdbcCall.execute(in);
    }

}
