package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.BeneficioDTO;
import com.backend_app.backend_app.dto.ServicioDTO;
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
public class ServicioFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ServicioFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ServicioDTO> buscarServicioPorNombre(String nombreServicio){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_SERVICIO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    ServicioDTO dto = new ServicioDTO();
                                    dto.setIdServicio(rs.getLong("ID_SERVICIO"));
                                    dto.setNombreServicio(rs.getString("NOMBRE_SERVICIO"));
                                    dto.setDescripcion(rs.getString("DESCRIPCION"));
                                    dto.setValorEstimado(rs.getBigDecimal("VALOR_ESTIMADO"));
                                    dto.setStock(rs.getInt("STOCK"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_SERVICIO_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_SERVICIO_BUSCAR", nombreServicio);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<ServicioDTO>) result.get("RETURN");
    }

}
