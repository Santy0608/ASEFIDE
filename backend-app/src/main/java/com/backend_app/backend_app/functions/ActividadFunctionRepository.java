package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.domain.Actividad;
import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class ActividadFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ActividadFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ActividadDTO> buscarActividadPorNombre(String nombre){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_ACTIVIDAD_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    ActividadDTO dto = new ActividadDTO();
                                    dto.setIdActividad(rs.getLong("ID_ACTIVIDAD"));
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    dto.setDescripcion(rs.getString("DESCRIPCION"));
                                    dto.setFechaEvento(rs.getDate("FECHA_EVENTO"));
                                    dto.setCupoTotal(rs.getInt("CUPO_TOTAL"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_BUSCAR", nombre);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<ActividadDTO>) result.get("RETURN");
    }

}
