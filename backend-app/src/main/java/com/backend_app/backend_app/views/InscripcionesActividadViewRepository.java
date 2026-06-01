package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.InscripcionUsuarioDTO;
import com.backend_app.backend_app.dto.InscripcionesActividadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InscripcionesActividadViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InscripcionesActividadViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<InscripcionesActividadDTO> getActividadesCompletas(){
        String sql = "SELECT ID_INSCRIPCION, ACTIVIDAD, NOMBRE_USUARIO, FECHA_INSCRIPCION, ASISTENCIA_CONFIRMADA, ESTADO FROM V_LISTAR_FIDE_INSCRIPCIONES_ACTIVIDAD_TB ORDER BY ID_INSCRIPCION DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            InscripcionesActividadDTO dto = new InscripcionesActividadDTO();
            dto.setIdInscripcion(rs.getLong("ID_INSCRIPCION"));
            dto.setNombreActividad(rs.getString("ACTIVIDAD"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setFechaInscripcion(rs.getDate("FECHA_INSCRIPCION"));
            dto.setAsistenciaConfirmada(rs.getBoolean("ASISTENCIA_CONFIRMADA"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }



}
