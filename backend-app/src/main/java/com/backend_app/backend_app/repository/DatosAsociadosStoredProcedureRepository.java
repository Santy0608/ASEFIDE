package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.DatosAsociadosDTO;
import com.backend_app.backend_app.dto.SqlArrayValue;
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
public class DatosAsociadosStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatosAsociadosStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertarDatosAsociados(DatosAsociadosDTO datosAsociados) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_DATOS_ASOCIADOS_INSERTAR_SP")
                .declareParameters(
                        new SqlParameter("P_ID_PUESTO_EMPRESA", Types.NUMERIC),
                        new SqlParameter("P_FECHA_AFILIACION", Types.DATE),
                        new SqlParameter("P_APORTES", Types.ARRAY, "SYS.ODCINUMBERLIST"),
                        new SqlOutParameter("P_ID_DATOS_ASOCIADOS", Types.NUMERIC)
                );
        Long[] aportesArray = datosAsociados.getAportes().stream()
                .map(a -> a.getMonto().longValue())
                .toArray(Long[]::new);

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_ID_PUESTO_EMPRESA", datosAsociados.getPuestoEmpresaId());
        inParams.put("P_FECHA_AFILIACION", datosAsociados.getFechaAfiliacion());
        inParams.put("P_APORTES", new SqlArrayValue(aportesArray, "SYS.ODCINUMBERLIST"));

        Map<String, Object> result = jdbcCall.execute(inParams);
        BigDecimal id = (BigDecimal) result.get("P_ID_DATOS_ASOCIADOS");
        return id.longValue();
    }


    public void editarDatosAsociados(DatosAsociadosDTO datosAsociadosDTO) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_DATOS_ASOCIADOS_EDITAR_SP")
                .declareParameters(
                        new SqlParameter("P_ID_DATOS_ASOCIADOS", Types.NUMERIC),
                        new SqlParameter("P_ID_PUESTO_EMPRESA", Types.NUMERIC),
                        new SqlParameter("P_FECHA_AFILIACION", Types.DATE),
                        new SqlParameter("P_APORTES", Types.ARRAY, "SYS.ODCINUMBERLIST")
                );

        Long[] aportesArray = datosAsociadosDTO.getAportes().stream()
                .map(a -> a.getMonto().longValue())
                .toArray(Long[]::new);

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_DATOS_ASOCIADOS", datosAsociadosDTO.getIdDatosAsociados())
                .addValue("P_ID_PUESTO_EMPRESA", datosAsociadosDTO.getPuestoEmpresaId())
                .addValue("P_FECHA_AFILIACION", datosAsociadosDTO.getFechaAfiliacion())
                .addValue("P_APORTES", new SqlArrayValue(aportesArray, "SYS.ODCINUMBERLIST"));

        jdbcCall.execute(in);
    }



}
