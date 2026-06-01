package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.domain.Prestamo;
import com.backend_app.backend_app.dto.EstadoDTO;
import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PrestamoFunctionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PrestamoFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EstadoDTO> usuariosPorEstado() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_PRESTAMOS_POR_ESTADO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    EstadoDTO dto = new EstadoDTO();
                                    dto.setNombre(rs.getString("NOMBRE_ESTADO"));
                                    dto.setCantidadPrestamos(rs.getInt("CANTIDAD_PRESTAMOS"));
                                    return dto;
                                })
                );
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return (List<EstadoDTO>) result.get("RETURN");
    }

}
