package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.DireccionDTO;
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
public class DireccionFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DireccionFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DireccionDTO> buscarDireccionPorProvincia(String provincia){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_DIRECCION_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    DireccionDTO dto = new DireccionDTO();
                                    dto.setIdDireccion(rs.getLong("ID_DIRECCION"));
                                    dto.setProvincia(rs.getString("PROVINCIA"));
                                    dto.setCanton(rs.getString("CANTON"));
                                    dto.setDistrito(rs.getString("DISTRITO"));
                                    return dto;
                                }),
                        new SqlParameter("P_DIRECCION_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_DIRECCION_BUSCAR", provincia);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<DireccionDTO>) result.get("RETURN");
    }

}
