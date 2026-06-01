package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.LugarEventoDTO;
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
public class LugarEventoFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LugarEventoFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LugarEventoDTO> buscarLugarEvento(String lugarEvento){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_LUGAR_EVENTO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    LugarEventoDTO dto = new LugarEventoDTO();
                                    dto.setIdLugarEvento(rs.getLong("ID_LUGAR_EVENTO"));
                                    dto.setNombreLugar(rs.getString("NOMBRE_LUGAR"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_LUGAR_EVENTO_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_LUGAR_EVENTO_BUSCAR", lugarEvento);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<LugarEventoDTO>) result.get("RETURN");
    }

}
