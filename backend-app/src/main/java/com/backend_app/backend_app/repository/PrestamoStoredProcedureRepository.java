package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.PrestamoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PrestamoStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PrestamoStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void registrarPrestamo(PrestamoDTO prestamo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_PRESTAMO_INSERTAR_SP")
                .declareParameters(
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC),
                        new SqlParameter("P_MONTO_SOLICITADO", Types.NUMERIC),
                        new SqlParameter("P_FECHA_APROBACION", Types.DATE),
                        new SqlParameter("P_SALDO_PENDIENTE", Types.NUMERIC),
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC),
                        new SqlParameter("P_TASA_INTERESES", Types.NUMERIC),
                        new SqlParameter("P_PLAZO_MESES", Types.NUMERIC)
                );

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_ID_USUARIO", prestamo.getUsuarioId());
        inParams.put("P_MONTO_SOLICITADO", prestamo.getMontoSolicitado());
        inParams.put("P_FECHA_APROBACION", prestamo.getFechaAprobacion());
        inParams.put("P_SALDO_PENDIENTE", prestamo.getSaldoPendiente());
        inParams.put("P_ID_ESTADO", 9);
        inParams.put("P_TASA_INTERESES", prestamo.getTasaIntereses());
        inParams.put("P_PLAZO_MESES", prestamo.getPlazoMeses());

        jdbcCall.execute(inParams);
    }


    public void editarPrestamo(PrestamoDTO prestamo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_PRESTAMO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_PRESTAMO", prestamo.getIdPrestamo())
                .addValue("P_ID_USUARIO", prestamo.getUsuarioId())
                .addValue("P_MONTO_SOLICITADO", prestamo.getMontoSolicitado())
                .addValue("P_FECHA_APROBACION", prestamo.getFechaAprobacion())
                .addValue("P_SALDO_PENDIENTE", prestamo.getSaldoPendiente())
                .addValue("P_ID_ESTADO", prestamo.getEstadoId())
                .addValue("P_TASA_INTERESES", prestamo.getTasaIntereses())
                .addValue("P_PLAZO_MESES", prestamo.getPlazoMeses());

        jdbcCall.execute(in);
    }

    public void eliminarPrestamo(PrestamoDTO prestamo){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_PRESTAMO_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_PRESTAMO", prestamo.getIdPrestamo());
        jdbcCall.execute(in);
    }


}
