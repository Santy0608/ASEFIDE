package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.InscripcionesActividadDTO;
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
public class InscripcionesActividadStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InscripcionesActividadStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertarInscripcion(InscripcionesActividadDTO inscripcion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_INSCRIPCIONES_ACTIVIDAD_INSERTAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_ACTIVIDAD", Types.NUMERIC),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC),
                        new SqlParameter("P_FECHA_INSCRIPCION", Types.TIMESTAMP),
                        new SqlParameter("P_ASISTENCIA_CONFIRMADA", Types.INTEGER), // Cambio clave: NUMERIC
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_ACTIVIDAD", inscripcion.getActividadId())
                .addValue("P_ID_USUARIO", inscripcion.getUsuarioId())
                .addValue("P_FECHA_INSCRIPCION", inscripcion.getFechaInscripcion())
                .addValue("P_ASISTENCIA_CONFIRMADA", inscripcion.isAsistenciaConfirmada() ? 1 : 0)
                .addValue("P_ID_ESTADO", 1);


        jdbcCall.execute(in);
    }


    public void editarInscripcionActividad(InscripcionesActividadDTO inscripcionesActividadDTO) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_INSCRIPCIONES_ACTIVIDAD_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_INSCRIPCION", inscripcionesActividadDTO.getIdInscripcion())
                .addValue("P_ID_ACTIVIDAD", inscripcionesActividadDTO.getActividadId())
                .addValue("P_ID_USUARIO", inscripcionesActividadDTO.getUsuarioId())
                .addValue("P_FECHA_INSCRIPCION", inscripcionesActividadDTO.getFechaInscripcion())
                .addValue("P_ASISTENCIA_CONFIRMADA", inscripcionesActividadDTO.isAsistenciaConfirmada())
                .addValue("P_ID_ESTADO", inscripcionesActividadDTO.getEstadoId());

        jdbcCall.execute(in);
    }

    public void eliminarInscripcionActividad(InscripcionesActividadDTO inscripcionesActividadDTO){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_INSCRIPCIONES_ACTIVIDAD_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_INSCRIPCION", inscripcionesActividadDTO.getIdInscripcion());
        jdbcCall.execute(in);
    }

}
