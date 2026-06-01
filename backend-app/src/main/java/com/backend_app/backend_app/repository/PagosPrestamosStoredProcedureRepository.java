package com.backend_app.backend_app.repository;


import com.backend_app.backend_app.dto.PagosPrestamosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class PagosPrestamosStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PagosPrestamosStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void registrarPagoPrestamo(PagosPrestamosDTO pago) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_PAGOS_PRESTAMOS_INSERTAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_ID_PRESTAMO", Types.NUMERIC),
                        new SqlParameter("P_MONTO_ABONADO", Types.NUMERIC),
                        new SqlParameter("P_FECHA_PAGO", Types.DATE)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TRANSACCION", pago.getTransaccionId())
                .addValue("P_ID_PRESTAMO", pago.getPrestamoId())
                .addValue("P_MONTO_ABONADO", pago.getMontoAbonado())
                .addValue("P_FECHA_PAGO", pago.getFechaPago());

        jdbcCall.execute(in);
    }

    public void editarPagoPrestamo(PagosPrestamosDTO pago) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_PAGOS_PRESTAMOS_EDITAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_PAGO", Types.NUMERIC),
                        new SqlParameter("P_ID_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_ID_PRESTAMO", Types.NUMERIC),
                        new SqlParameter("P_MONTO_ABONADO", Types.NUMERIC),
                        new SqlParameter("P_FECHA_PAGO", Types.DATE)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_PAGO", pago.getIdPago())
                .addValue("P_ID_TRANSACCION", pago.getTransaccionId())
                .addValue("P_ID_PRESTAMO", pago.getPrestamoId())
                .addValue("P_MONTO_ABONADO", pago.getMontoAbonado())
                .addValue("P_FECHA_PAGO", pago.getFechaPago());

        jdbcCall.execute(in);
    }


}
