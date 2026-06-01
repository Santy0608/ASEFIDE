package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.ModuloReporteDTO;
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
public class ModuloReporteFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ModuloReporteFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ModuloReporteDTO> buscarModuloReportePorNombre(String nombreModuloReporte){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_MODULO_REPORTE_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    ModuloReporteDTO dto = new ModuloReporteDTO();
                                    dto.setIdModulo(rs.getLong("ID_MODULO"));
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    dto.setDescripcion(rs.getString("DESCRIPCION"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_MODULO_REPORTE_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_MODULO_REPORTE_BUSCAR", nombreModuloReporte);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<ModuloReporteDTO>) result.get("RETURN");
    }

}
