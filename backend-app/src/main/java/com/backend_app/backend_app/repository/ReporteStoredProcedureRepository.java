package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.PrestamoDTO;
import com.backend_app.backend_app.dto.ReporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReporteStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReporteStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void registrarReporte(ReporteDTO reporteDTO) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_REPORTES_INSERTAR_SP")
                .declareParameters(
                        new SqlParameter("P_ID_TIPO_REPORTE", Types.NUMERIC),
                        new SqlParameter("P_FECHA_INICIO", Types.DATE),
                        new SqlParameter("P_FECHA_FINAL", Types.DATE),
                        new SqlParameter("P_ID_MODULO", Types.NUMERIC),
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC)
                );

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_ID_TIPO_REPORTE", reporteDTO.getTipoReporteId());
        inParams.put("P_FECHA_INICIO", reporteDTO.getFechaInicio());
        inParams.put("P_FECHA_FINAL", reporteDTO.getFechaFinal());
        inParams.put("P_ID_MODULO", reporteDTO.getIdModuloReporte());
        inParams.put("P_ID_ESTADO", 9);
        inParams.put("P_ID_USUARIO", reporteDTO.getUsuarioId());

        jdbcCall.execute(inParams);
    }

    public List<ReporteDTO> generarReporte(Date fechaInicio, Date fechaFinal) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_GENERAR_REPORTE_SP")
                .returningResultSet("P_CURSOR", BeanPropertyRowMapper.newInstance(ReporteDTO.class));

        Map<String, Object> result = jdbcCall.execute(Map.of(
                "P_FECHA_INICIO", fechaInicio,
                "P_FECHA_FINAL", fechaFinal
        ));

        return (List<ReporteDTO>) result.get("P_CURSOR");
    }

    public Long obtenerNumeroReporte() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_REPORTE_NUM_FN")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.NUMERIC)
                );
        Map<String, Object> result = jdbcCall.execute();
        BigDecimal numero = (BigDecimal) result.get("RETURN");
        return numero.longValue();
    }


    public void editarReporte(ReporteDTO reporteDTO) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_REPORTES_EDITAR_SP")
                // Esto evita que Spring intente adivinar el orden de los parámetros
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_REPORTE", Types.NUMERIC),
                        new SqlParameter("P_ID_TIPO_REPORTE", Types.NUMERIC),
                        new SqlParameter("P_FECHA_INICIO", Types.DATE),
                        new SqlParameter("P_FECHA_FINAL", Types.DATE),
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC),
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC)
                );

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_REPORTE", reporteDTO.getIdModuloReporte())
                .addValue("P_ID_TIPO_REPORTE", reporteDTO.getTipoReporteId())
                .addValue("P_FECHA_INICIO", reporteDTO.getFechaInicio())
                .addValue("P_FECHA_FINAL", reporteDTO.getFechaFinal())
                .addValue("P_ID_USUARIO", reporteDTO.getUsuarioId())
                .addValue("P_ID_ESTADO", reporteDTO.getEstadoId());

        jdbcCall.execute(in);
    }

    public void eliminarReporte(ReporteDTO reporteDTO){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_REPORTE_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_REPORTE", reporteDTO.getIdReporte());
        jdbcCall.execute(in);
    }



}
