package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.BeneficioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BeneficioViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BeneficioViewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BeneficioDTO> getBeneficiosAsociados() {
        String sql = "SELECT ID_BENEFICIO, NOMBRE_BENEFICIO, DESCRIPCION, IMAGEN_URL FROM V_FIDE_BENEFICIOS_ASOCIADOS";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            BeneficioDTO dto = new BeneficioDTO();

            dto.setIdBeneficio(rs.getLong("ID_BENEFICIO"));
            dto.setNombreBeneficio(rs.getString("NOMBRE_BENEFICIO"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setImagenUrl(rs.getString("IMAGEN_URL"));
            return dto;
        });
    }

    public List<BeneficioDTO> getBeneficiosCompletos(){
        String sql = "SELECT ID_BENEFICIO, NOMBRE_BENEFICIO, DESCRIPCION, IMAGEN_URL, CATEGORIA, ESTADO FROM V_LISTAR_FIDE_BENEFICIO_TB ORDER BY ID_BENEFICIO DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            BeneficioDTO dto = new BeneficioDTO();

            dto.setIdBeneficio(rs.getLong("ID_BENEFICIO"));
            dto.setNombreBeneficio(rs.getString("NOMBRE_BENEFICIO"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setImagenUrl(rs.getString("IMAGEN_URL"));
            dto.setNombreCategoria(rs.getString("CATEGORIA"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
