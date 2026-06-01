package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.dto.CategoriaDTO;
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
public class CategoriaFunctionRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoriaFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CategoriaDTO> buscarCategoria(String nombreCategoria){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_CATEGORIA_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    CategoriaDTO dto = new CategoriaDTO();
                                    dto.setIdCategoria(rs.getLong("ID_CATEGORIA"));
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_CATEGORIA_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_CATEGORIA_BUSCAR", nombreCategoria);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<CategoriaDTO>) result.get("RETURN");
    }

}
