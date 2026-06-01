package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.MovimientosAhorroDTO;
import org.hibernate.annotations.processing.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class MovimientosAhorroStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MovimientosAhorroStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registrarMovimientoAhorro(MovimientosAhorroDTO movimiento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_MOVIMIENTOS_AHORRO_INSERTAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_AHORRO", Types.NUMERIC),
                        new SqlParameter("P_ID_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_MONTO", Types.NUMERIC),
                        new SqlParameter("P_FECHA_DEPOSITO", Types.DATE),
                        new SqlParameter("P_TIPO_MOVIMIENTO", Types.VARCHAR)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_AHORRO", movimiento.getCuentasAhorroId())
                .addValue("P_ID_TRANSACCION", movimiento.getTransaccionId())
                .addValue("P_MONTO", movimiento.getMonto())
                .addValue("P_FECHA_DEPOSITO", movimiento.getFechaDeposito())
                .addValue("P_TIPO_MOVIMIENTO", movimiento.getTipoMovimiento());

        jdbcCall.execute(in);
    }

    public void editarMovimientoAhorro(MovimientosAhorroDTO movimiento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_MOVIMIENTOS_AHORRO_EDITAR_SP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_MOVIMIENTO", Types.NUMERIC),
                        new SqlParameter("P_ID_AHORRO", Types.NUMERIC),
                        new SqlParameter("P_ID_TRANSACCION", Types.NUMERIC),
                        new SqlParameter("P_MONTO", Types.NUMERIC),
                        new SqlParameter("P_FECHA_DEPOSITO", Types.DATE)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_MOVIMIENTO", movimiento.getIdMovimiento())
                .addValue("P_ID_AHORRO", movimiento.getCuentasAhorroId())
                .addValue("P_ID_TRANSACCION", movimiento.getTransaccionId())
                .addValue("P_MONTO", movimiento.getMonto())
                .addValue("P_FECHA_DEPOSITO", movimiento.getFechaDeposito());

        jdbcCall.execute(in);
    }

}
