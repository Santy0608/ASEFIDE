package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CategoriaStoredProcedureRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoriaStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarCategoria(CategoriaDTO categoria) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_CATEGORIA_INSERTAR_SP"); // Nombre del SP

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE", categoria.getNombre());
        inParams.put("P_DESCRIPCION", categoria.getDescripcion());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarCategoria(CategoriaDTO categoria) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_CATEGORIA_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_CATEGORIA", categoria.getIdCategoria()) // Identificador clave
                .addValue("P_NOMBRE", categoria.getNombre())
                .addValue("P_DESCRIPCION", categoria.getDescripcion())
                .addValue("P_ID_ESTADO", 1);
        jdbcCall.execute(in);
    }

    public void eliminarCategoria(CategoriaDTO categoria){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_CATEGORIA_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_CATEGORIA", categoria.getIdCategoria());
        jdbcCall.execute(in);
    }

}
