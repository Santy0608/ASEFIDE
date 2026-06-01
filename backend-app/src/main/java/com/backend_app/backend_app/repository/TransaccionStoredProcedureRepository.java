package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.TransaccionDTO;
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
public class TransaccionStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TransaccionStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Long insertarTransaccion(TransaccionDTO transaccion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG") // Nombre del paquete
                .withProcedureName("FIDE_TRANSACCION_INSERTAR_SP")
                .declareParameters(
                        new SqlParameter("P_FECHA_TRANSACCION", Types.DATE),
                        new SqlParameter("P_ID_TIPO_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_MONTO_TOTAL", Types.DOUBLE),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC),
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC),

                        new SqlOutParameter("P_ID_TRANSACCION", Types.NUMERIC)
                );

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_FECHA_TRANSACCION", transaccion.getFechaTransaccion());
        inParams.put("P_ID_TIPO_TRANSACCION", transaccion.getTipoTransaccionId());
        inParams.put("P_MONTO_TOTAL", transaccion.getMontoTotal());
        inParams.put("P_ID_USUARIO", transaccion.getUsuarioId());
        inParams.put("P_ID_ESTADO", 9);
        Map<String, Object> result = jdbcCall.execute(inParams);

        BigDecimal id = (BigDecimal) result.get("P_ID_TRANSACCION");

        return id.longValue();
    }


    public void editarTransaccion(TransaccionDTO transaccion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TRANSACCION_EDITAR_SP")
                // Agrega esta línea para evitar que Spring intente "adivinar"
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_FECHA_TRANSACCION", Types.DATE),
                        new SqlParameter("P_ID_TIPO_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_MONTO_TOTAL", Types.NUMERIC),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC),
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TRANSACCION", transaccion.getIdTransaccion())
                .addValue("P_FECHA_TRANSACCION", transaccion.getFechaTransaccion())
                .addValue("P_ID_TIPO_TRANSACCION", transaccion.getTipoTransaccionId())
                .addValue("P_MONTO_TOTAL", transaccion.getMontoTotal())
                .addValue("P_ID_USUARIO", transaccion.getUsuarioId())
                .addValue("P_ID_ESTADO", transaccion.getEstadoId());

        jdbcCall.execute(in);
    }

    public void eliminarTransaccion(TransaccionDTO transaccion){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TRANSACCION_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TRANSACCION", transaccion.getIdTransaccion());
        jdbcCall.execute(in);
    }


}
