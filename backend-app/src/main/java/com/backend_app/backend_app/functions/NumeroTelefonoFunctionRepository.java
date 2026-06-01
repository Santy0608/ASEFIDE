package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.TelefonoDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
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
public class NumeroTelefonoFunctionRepository {

    public JdbcTemplate jdbcTemplate;

    @Autowired
    public NumeroTelefonoFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TelefonoDTO> buscarPorNumeroTelefono(String numeroTelefono){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_NUMERO_TELEFONO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    TelefonoDTO dto = new TelefonoDTO();
                                    dto.setIdTelefono(rs.getLong("ID_NUMERO"));
                                    dto.setNumeroTelefono(rs.getString("NUMERO_TELEFONO"));
                                    return dto;
                                }),
                        new SqlParameter("P_NUMERO_TELEFONO_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NUMERO_TELEFONO_BUSCAR", numeroTelefono);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<TelefonoDTO>) result.get("RETURN");

    }




}
