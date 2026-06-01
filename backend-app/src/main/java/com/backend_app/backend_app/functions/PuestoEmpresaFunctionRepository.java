package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.DireccionDTO;
import com.backend_app.backend_app.dto.PuestoEmpresaDTO;
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
public class PuestoEmpresaFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PuestoEmpresaFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PuestoEmpresaDTO> buscarPuestoEmpresa(String puestoEmpresa){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_PUESTO_EMPRESA_ASOCIADO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    PuestoEmpresaDTO dto = new PuestoEmpresaDTO();
                                    dto.setIdPuestoEmpresa(rs.getLong("ID_PUESTO_EMPRESA"));
                                    dto.setPuestoEmpresa(rs.getString("PUESTO_EMPRESA"));
                                    return dto;
                                }),
                        new SqlParameter("P_PUESTO_EMPRESA_ASOCIADO_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_PUESTO_EMPRESA_ASOCIADO_BUSCAR", puestoEmpresa);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<PuestoEmpresaDTO>) result.get("RETURN");
    }

}
