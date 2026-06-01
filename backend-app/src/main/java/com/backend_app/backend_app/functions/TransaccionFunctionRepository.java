package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.TransaccionDTO;
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
public class TransaccionFunctionRepository {

    public JdbcTemplate jdbcTemplate;

    @Autowired
    public TransaccionFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer historialTransacciones(Integer idUsuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_HISTORIAL_TRANSACCIONES_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.NUMERIC),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC)
                );

        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_ID_USUARIO", idUsuario);

        Map<String, Object> result = jdbcCall.execute(inParams);
        return ((Number) result.get("RETURN")).intValue();
    }

    public Integer cantidadTransaccionesUsuario(Long idUsuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_CANTIDAD_TRANSACCIONES_USUARIO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.NUMERIC),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC)
                );

        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_ID_USUARIO", idUsuario);

        Map<String, Object> result = jdbcCall.execute(inParams);
        return ((Number) result.get("RETURN")).intValue();
    }

    public List<TransaccionDTO> top5Transacciones() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_TOP5_USUARIOS_TRANSACCIONES_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    TransaccionDTO dto = new TransaccionDTO();
                                    dto.setUsuarioId(rs.getLong("ID_USUARIO"));
                                    dto.setNombreUsuario(rs.getString("NOMBRE"));
                                    dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                                    dto.setTotalTransacciones(rs.getInt("TOTAL_TRANSACCIONES"));
                                    return dto;
                                })
                );
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return (List<TransaccionDTO>) result.get("RETURN");
    }

    public List<TransaccionDTO> historialTransacciones(Long idUsuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_HISTORIAL_TRANSACCIONES_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    TransaccionDTO dto = new TransaccionDTO();
                                    dto.setIdTransaccion(rs.getLong("ID_TRANSACCION"));
                                    dto.setFechaTransaccion(rs.getDate("FECHA_TRANSACCION"));
                                    dto.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
                                    return dto;
                                }),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_ID_USUARIO", idUsuario);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<TransaccionDTO>) result.get("RETURN");
    }



}

