package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.DetalleTransaccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class DetalleTransaccionStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DetalleTransaccionStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registrarDetalleTransaccion(DetalleTransaccionDTO detalle) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_DETALLE_TRANSACCION_INSERTAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_CONCEPTO", Types.VARCHAR),
                        new SqlParameter("P_SUB_TOTAL", Types.NUMERIC)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TRANSACCION", detalle.getTransaccionId())
                .addValue("P_CONCEPTO", detalle.getConcepto())
                .addValue("P_SUB_TOTAL", detalle.getSubTotal());

        jdbcCall.execute(in);
    }

    public void editarDetalleTransaccion(DetalleTransaccionDTO detalle) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_DETALLE_TRANSACCION_EDITAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_DETALLE", Types.NUMERIC),
                        new SqlParameter("P_ID_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_CONCEPTO", Types.VARCHAR),
                        new SqlParameter("P_SUB_TOTAL", Types.NUMERIC)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_DETALLE", detalle.getIdDetalle())
                .addValue("P_ID_TRANSACCION", detalle.getTransaccionId())
                .addValue("P_CONCEPTO", detalle.getConcepto())
                .addValue("P_SUB_TOTAL", detalle.getSubTotal());

        jdbcCall.execute(in);
    }


}
