package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.ActividadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActividadViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActividadViewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ActividadDTO> getActividadesProgramadas() {
        String sql = "SELECT ID_ACTIVIDAD, NOMBRE_ACTIVIDAD, FECHA_EVENTO, ID_LUGAR_EVENTO, CUPO_TOTAL, NOMBRE_COMPLETO_RESPONSABLE, ESTADO_ACTIVIDAD FROM V_FIDE_ACTIVIDADES_PROGRAMADAS ORDER BY ID_ACTIVIDAD DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ActividadDTO dto = new ActividadDTO();
            dto.setIdActividad(rs.getLong("ID_ACTIVIDAD"));
            dto.setNombre(rs.getString("NOMBRE_ACTIVIDAD"));
            dto.setFechaEvento(rs.getDate("FECHA_EVENTO"));
            dto.setLugarEventoId(rs.getLong("ID_LUGAR_EVENTO"));
            dto.setCupoTotal(rs.getInt("CUPO_TOTAL"));
            dto.setNombre(rs.getString("NOMBRE_COMPLETO_RESPONSABLE"));
            dto.setEstadoActividad(rs.getString("ESTADO_ACTIVIDAD"));
            return dto;
        });
    }

    public List<ActividadDTO> getActividadesAsociados() {
        String sql = "SELECT ID_ACTIVIDAD, ACTIVIDAD_NOMBRE, DESCRIPCION, FECHA_EVENTO, CUPO_TOTAL, NOMBRE_LUGAR, ENCARGADO_NOMBRE, APELLIDO_PATERNO, IMAGEN_URL FROM V_FIDE_ACTIVIDADES_ASOCIADOS";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ActividadDTO dto = new ActividadDTO();

            dto.setIdActividad(rs.getLong("ID_ACTIVIDAD"));
            dto.setNombre(rs.getString("ACTIVIDAD_NOMBRE"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setFechaEvento(rs.getDate("FECHA_EVENTO"));
            dto.setCupoTotal(rs.getInt("CUPO_TOTAL"));

            dto.setNombreLugarEvento(rs.getString("NOMBRE_LUGAR"));

            dto.setNombreUsuario(rs.getString("ENCARGADO_NOMBRE"));
            dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
            dto.setImagenUrl(rs.getString("IMAGEN_URL"));
            return dto;
        });
    }

    public List<ActividadDTO> getActividadesCompletas(){
        String sql = "SELECT ID_ACTIVIDAD, NOMBRE, DESCRIPCION, FECHA_EVENTO, NOMBRE_LUGAR, CUPO_TOTAL, ESTADO, CREADOR_EVENTO FROM V_LISTAR_FIDE_ACTIVIDAD_TB ORDER BY ID_ACTIVIDAD DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ActividadDTO dto = new ActividadDTO();

            dto.setIdActividad(rs.getLong("ID_ACTIVIDAD"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setFechaEvento(rs.getDate("FECHA_EVENTO"));
            dto.setNombreLugarEvento(rs.getString("NOMBRE_LUGAR"));
            dto.setCupoTotal(rs.getInt("CUPO_TOTAL"));
            dto.setEstadoActividad(rs.getString("ESTADO"));
            dto.setNombreUsuario(rs.getString("CREADOR_EVENTO"));
            return dto;
        });
    }

}
