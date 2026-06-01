package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.TelefonoDTO;
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
public class CorreoElectronicoFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CorreoElectronicoFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CorreoDTO> buscarPorCorreoElectronico(String correoElectronico){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_CORREO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    CorreoDTO dto = new CorreoDTO();
                                    dto.setIdCorreo(rs.getLong("ID_CORREO"));
                                    dto.setCorreoElectronico(rs.getString("CORREO_ELECTRONICO"));
                                    return dto;
                                }),
                        new SqlParameter("P_CORREO_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_CORREO_BUSCAR", correoElectronico);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<CorreoDTO>) result.get("RETURN");
    }


}
