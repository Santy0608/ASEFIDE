package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.PuestoEmpresaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoriaViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<CategoriaDTO> getCategoriasCompletas() {
        String sql = "SELECT ID_CATEGORIA, NOMBRE, DESCRIPCION, ESTADO FROM V_LISTAR_FIDE_CATEGORIA_TB ORDER BY ID_CATEGORIA DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setIdCategoria(rs.getLong("ID_CATEGORIA"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }



}

