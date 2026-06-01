package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.TipoReporteDTO;
import com.backend_app.backend_app.dto.TipoTransaccionDTO;
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
public class TipoTransaccionFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoTransaccionFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TipoTransaccionDTO> buscarTipoTransaccionPorNombre(String nombreTipoTransaccion){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_TIPO_TRANSACCION_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    TipoTransaccionDTO dto = new TipoTransaccionDTO();
                                    dto.setIdTipoTransaccion(rs.getLong("ID_TIPO_TRANSACCION"));
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    dto.setDescripcion(rs.getString("DESCRIPCION"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_TIPO_TRANSACCION_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_TIPO_TRANSACCION_BUSCAR", nombreTipoTransaccion);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<TipoTransaccionDTO>) result.get("RETURN");
    }

}
