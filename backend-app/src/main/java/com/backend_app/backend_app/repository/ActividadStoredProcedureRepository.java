package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.domain.Actividad;
import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ActividadStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ActividadStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertarActividad(ActividadDTO actividad) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG") // Nombre del paquete
                .withProcedureName("FIDE_ACTIVIDAD_INSERTAR_SP");

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE", actividad.getNombre());
        inParams.put("P_DESCRIPCION", actividad.getDescripcion());
        inParams.put("P_FECHA_EVENTO", actividad.getFechaEvento());
        inParams.put("P_CUPO_TOTAL", actividad.getCupoTotal());
        inParams.put("P_ID_ESTADO", 1);
        inParams.put("P_ID_USUARIO", actividad.getUsuarioId());
        inParams.put("P_ID_LUGAR_EVENTO", actividad.getLugarEventoId());

        jdbcCall.execute(inParams);
    }


    public void editarActividad(ActividadDTO actividad) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_ACTIVIDAD_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_ACTIVIDAD", actividad.getIdActividad()) // Identificador clave
                .addValue("P_NOMBRE", actividad.getNombre())
                .addValue("P_DESCRIPCION", actividad.getDescripcion())
                .addValue("P_FECHA_EVENTO", actividad.getFechaEvento())
                .addValue("P_CUPO_TOTAL", actividad.getCupoTotal())
                .addValue("P_ID_ESTADO", actividad.getEstadoId())
                .addValue("P_ID_USUARIO", actividad.getUsuarioId())
                .addValue("P_ID_LUGAR_EVENTO", actividad.getEstadoId());

        jdbcCall.execute(in);
    }

    public void eliminarActividad(ActividadDTO actividad){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_ACTIVIDAD_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_ACTIVIDAD", actividad.getIdActividad());
        jdbcCall.execute(in);
    }



}
