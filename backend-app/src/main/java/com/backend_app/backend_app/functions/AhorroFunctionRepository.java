package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.hibernate.dialect.OracleTypes;


import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class AhorroFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AhorroFunctionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Double sumaTotalAhorros() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_SUMA_TOTAL_AHORROS_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.NUMERIC)
                );

        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return ((Number) result.get("RETURN")).doubleValue();  // ← doubleValue para montos
    }

    // Promedio de ahorros
    public Double promedioAhorros() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_PROMEDIO_AHORROS_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.NUMERIC)
                );

        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return ((Number) result.get("RETURN")).doubleValue();
    }

    // Reporte Ahorros Usuario
    public List<CuentasAhorroDTO> reporteAhorrosUsuario(Long idUsuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_REPORTE_AHORROS_USUARIO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    CuentasAhorroDTO dto = new CuentasAhorroDTO();
                                    dto.setIdAhorro(rs.getLong("ID_AHORRO"));
                                    dto.setMontoAporte(rs.getBigDecimal("MONTO_APORTE"));
                                    dto.setSaldoActual(rs.getBigDecimal("SALDO_ACTUAL"));
                                    return dto;
                                }),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_ID_USUARIO", idUsuario);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<CuentasAhorroDTO>) result.get("RETURN");
    }


    // Top 10 Más Ahorros
    public List<CuentasAhorroDTO> top10MasAhorros() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_TOP10_MAS_AHORROS_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    CuentasAhorroDTO dto = new CuentasAhorroDTO();
                                    dto.setNombreUsuario(rs.getString("NOMBRE"));
                                    dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                                    dto.setTotalAhorro(rs.getBigDecimal("TOTAL_AHORRO"));
                                    return dto;
                                })
                );
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return (List<CuentasAhorroDTO>) result.get("RETURN");
    }

    // Top 10 Menos Ahorro
    public List<CuentasAhorroDTO> top10MenosAhorros() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_TOP10_MENOS_AHORROS_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    CuentasAhorroDTO dto = new CuentasAhorroDTO();
                                    dto.setNombreUsuario(rs.getString("NOMBRE"));
                                    dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                                    dto.setTotalAhorro(rs.getBigDecimal("TOTAL_AHORRO"));
                                    return dto;
                                })
                );
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return (List<CuentasAhorroDTO>) result.get("RETURN");
    }

    //Llamando función para obtener los usuarios con mayor ahorro
    public List<CuentasAhorroDTO> usuariosMayorAhorro(BigDecimal monto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_USUARIOS_MAYOR_AHORRO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    CuentasAhorroDTO dto = new CuentasAhorroDTO();
                                    dto.setNombreUsuario(rs.getString("NOMBRE"));
                                    dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                                    dto.setSaldoActual(rs.getBigDecimal("SALDO_ACTUAL"));
                                    return dto;
                                }),
                        new SqlParameter("P_MONTO", Types.NUMERIC)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_MONTO", monto);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<CuentasAhorroDTO>) result.get("RETURN");
    }


}


