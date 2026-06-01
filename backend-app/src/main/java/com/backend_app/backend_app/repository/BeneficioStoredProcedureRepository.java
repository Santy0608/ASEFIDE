package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.BeneficioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BeneficioStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BeneficioStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertarBeneficio(BeneficioDTO beneficio) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG") // Nombre del paquete
                .withProcedureName("FIDE_BENEFICIO_INSERTAR_SP");

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE_BENEFICIO", beneficio.getNombreBeneficio());
        inParams.put("P_DESCRIPCION", beneficio.getDescripcion());
        inParams.put("P_ID_CATEGORIA", beneficio.getCategoriaId());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarBeneficio(BeneficioDTO beneficio) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_BENEFICIO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_BENEFICIO", beneficio.getIdBeneficio()) // Identificador clave
                .addValue("P_NOMBRE_BENEFICIO", beneficio.getNombreBeneficio())
                .addValue("P_DESCRIPCION", beneficio.getDescripcion())
                .addValue("P_ID_CATEGORIA", beneficio.getCategoriaId())
                .addValue("P_ID_ESTADO", beneficio.getEstadoId());

        jdbcCall.execute(in);
    }

    public void eliminarBeneficio(BeneficioDTO beneficio){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_BENEFICIO_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_BENEFICIO", beneficio.getIdBeneficio());
        jdbcCall.execute(in);
    }

}
