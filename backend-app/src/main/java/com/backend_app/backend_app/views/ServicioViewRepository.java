package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.BeneficioDTO;
import com.backend_app.backend_app.dto.ServicioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicioViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ServicioViewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ServicioDTO> getServiciosAsociados() {
        String sql = "SELECT ID_SERVICIO, NOMBRE_SERVICIO, DESCRIPCION, VALOR_ESTIMADO, STOCK, IMAGEN_URL FROM V_FIDE_SERVICIOS_ASOCIADOS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ServicioDTO dto = new ServicioDTO();
            dto.setIdServicio(rs.getLong("ID_SERVICIO"));
            dto.setNombreServicio(rs.getString("NOMBRE_SERVICIO"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setValorEstimado(rs.getBigDecimal("VALOR_ESTIMADO"));
            dto.setStock(rs.getInt("STOCK"));
            dto.setImagenUrl(rs.getString("IMAGEN_URL"));
            return dto;
        });
    }

    public List<ServicioDTO> getServiciosCompletos() {
        String sql = "SELECT ID_SERVICIO, NOMBRE_SERVICIO, DESCRIPCION, VALOR_ESTIMADO, STOCK, CATEGORIA, ESTADO, IMAGEN_URL FROM V_LISTAR_FIDE_SERVICIO_TB ORDER BY ID_SERVICIO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ServicioDTO dto = new ServicioDTO();
            dto.setIdServicio(rs.getLong("ID_SERVICIO"));
            dto.setNombreServicio(rs.getString("NOMBRE_SERVICIO"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setValorEstimado(rs.getBigDecimal("VALOR_ESTIMADO"));
            dto.setStock(rs.getInt("STOCK"));
            dto.setNombreCategoria(rs.getString("CATEGORIA"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            dto.setImagenUrl(rs.getString("IMAGEN_URL"));
            return dto;
        });
    }

}
