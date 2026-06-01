package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.PuestoEmpresaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PuestoEmpresaStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PuestoEmpresaStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresa) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_PUESTO_EMPRESA_ASOCIADO_INSERTAR_SP")
                .declareParameters(new SqlParameter("P_PUESTO_EMPRESA", Types.VARCHAR),
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC));
        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_PUESTO_EMPRESA", puestoEmpresa.getPuestoEmpresa());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresa) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_PUESTO_EMPRESA_ASOCIADO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_PUESTO_EMPRESA", puestoEmpresa.getIdPuestoEmpresa()) // Identificador clave
                .addValue("P_PUESTO_EMPRESA", puestoEmpresa.getPuestoEmpresa())
                .addValue("P_ID_ESTADO", 1);
        jdbcCall.execute(in);
    }

    public void eliminarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresaDTO){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_PUESTO_EMPRESA_ASOCIADO_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_PUESTO_EMPRESA", puestoEmpresaDTO.getIdPuestoEmpresa());
        jdbcCall.execute(in);
    }

}
