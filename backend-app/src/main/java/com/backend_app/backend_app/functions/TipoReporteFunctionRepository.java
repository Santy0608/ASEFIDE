package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.domain.TipoReporte;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.dto.TipoReporteDTO;
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
public class TipoReporteFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoReporteFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TipoReporteDTO> buscarTipoReportePorNombre(String nombreTipoReporte){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_TIPO_REPORTE_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    TipoReporteDTO dto = new TipoReporteDTO();
                                    dto.setIdTipoReporte(rs.getLong("ID_TIPO_REPORTE"));
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_TIPO_REPORTE_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_TIPO_REPORTE_BUSCAR", nombreTipoReporte);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<TipoReporteDTO>) result.get("RETURN");
    }

}
