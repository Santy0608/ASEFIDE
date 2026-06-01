package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CuentasAhorroStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CuentasAhorroStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertarCuentaAhorro(CuentasAhorroDTO cuentasAhorro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG") // Nombre del paquete
                .withProcedureName("FIDE_CUENTAS_AHORRO_INSERTAR_SP")
                .declareParameters(
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC),
                        new SqlParameter("P_MONTO_APORTE", Types.NUMERIC),
                        new SqlParameter("P_FECHA_APERTURA", Types.DATE),
                        new SqlParameter("P_ID_TIPO_AHORRO", Types.NUMERIC),
                        new SqlParameter("P_SALDO_ACTUAL", Types.NUMERIC),
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC),
                        new SqlOutParameter("P_ID_AHORRO", Types.NUMERIC)
                );

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_ID_USUARIO", cuentasAhorro.getUsuarioId());
        inParams.put("P_MONTO_APORTE", cuentasAhorro.getMontoAporte());
        inParams.put("P_FECHA_APERTURA", cuentasAhorro.getFechaApertura());
        inParams.put("P_ID_TIPO_AHORRO", cuentasAhorro.getTipoAhorroId());
        inParams.put("P_SALDO_ACTUAL", cuentasAhorro.getSaldoActual());
        inParams.put("P_ID_ESTADO", 1);

        Map<String, Object> result = jdbcCall.execute(inParams);
        BigDecimal id = (BigDecimal) result.get("P_ID_AHORRO");
        return id.longValue();
    }


    public void editarCuentasAhorro(CuentasAhorroDTO cuentasAhorro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_CUENTAS_AHORRO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_AHORRO", cuentasAhorro.getIdAhorro()) // Identificador clave
                .addValue("P_ID_USUARIO", cuentasAhorro.getUsuarioId())
                .addValue("P_MONTO_APORTE", cuentasAhorro.getMontoAporte())
                .addValue("P_FECHA_APERTURA", cuentasAhorro.getFechaApertura())
                .addValue("P_ID_TIPO_AHORRO", cuentasAhorro.getTipoAhorroId())
                .addValue("P_SALDO_ACTUAL", cuentasAhorro.getSaldoActual())
                .addValue("P_ID_ESTADO", cuentasAhorro.getEstadoId());

        jdbcCall.execute(in);
    }

    public void eliminarCuentasAhorro(CuentasAhorroDTO cuentasAhorro){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_CUENTAS_AHORRO_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_AHORRO", cuentasAhorro.getIdAhorro());
        jdbcCall.execute(in);
    }

}
