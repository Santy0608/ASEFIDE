package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
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
public class TipoAhorroFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoAhorroFunctionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TipoAhorroDTO> buscarActividadPorTipoAhorro(String nombreTipoAhorro){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_TIPO_AHORRO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    TipoAhorroDTO dto = new TipoAhorroDTO();
                                    dto.setIdTipoAhorro(rs.getLong("ID_TIPO_AHORRO"));
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    dto.setDescripcion(rs.getString("DESCRIPCION"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_TIPO_AHORRO_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_TIPO_AHORRO_BUSCAR", nombreTipoAhorro);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<TipoAhorroDTO>) result.get("RETURN");
    }

}
