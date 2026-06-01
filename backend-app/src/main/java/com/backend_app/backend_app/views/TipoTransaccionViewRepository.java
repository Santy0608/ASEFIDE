package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.TipoTransaccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoTransaccionViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoTransaccionViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TipoTransaccionDTO> getTiposTransaccionesCompletas() {
        String sql = "SELECT ID_TIPO_TRANSACCION, NOMBRE, DESCRIPCION, ESTADO FROM V_LISTAR_FIDE_TIPO_TRANSACCION_TB ORDER BY ID_TIPO_TRANSACCION DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TipoTransaccionDTO dto = new TipoTransaccionDTO();
            dto.setIdTipoTransaccion(rs.getLong("ID_TIPO_TRANSACCION"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
