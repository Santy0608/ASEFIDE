package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.AporteDTO;
import com.backend_app.backend_app.dto.DatosAsociadosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatosAsociadosViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatosAsociadosViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DatosAsociadosDTO> getDatosAsociadosCompletos() {
        String sql = """
        SELECT ID_USUARIO, ID_DATOS_ASOCIADOS, NOMBRE_COMPLETO, NOMBRE_USUARIO,
               CANTIDAD_APORTES, TOTAL_APORTES, APORTE_VIGENTE,
               PUESTO_EMPRESA, FECHA_AFILIACION, ESTADO_USUARIO
        FROM V_FIDE_APORTES_USUARIO ORDER BY FECHA_AFILIACION DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DatosAsociadosDTO dto = new DatosAsociadosDTO();
            dto.setIdUsuario(rs.getLong("ID_USUARIO"));
            dto.setIdDatosAsociados(rs.getLong("ID_DATOS_ASOCIADOS"));
            dto.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setCantidadAportes(rs.getInt("CANTIDAD_APORTES"));
            dto.setTotalAportes(rs.getBigDecimal("TOTAL_APORTES"));
            dto.setAporteVigente(rs.getBigDecimal("APORTE_VIGENTE"));
            dto.setPuestoEmpresa(rs.getString("PUESTO_EMPRESA"));
            dto.setFechaAfiliacion(rs.getDate("FECHA_AFILIACION"));
            dto.setEstadoUsuario(rs.getString("ESTADO_USUARIO"));
            return dto;
        });
    }

}
