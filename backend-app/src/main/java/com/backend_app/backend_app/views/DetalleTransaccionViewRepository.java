package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.DetalleTransaccionDTO;
import com.backend_app.backend_app.dto.TransaccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DetalleTransaccionViewRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DetalleTransaccionViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DetalleTransaccionDTO> getDetallesTranasaccionesCompletas() {
        String sql = "SELECT ID_DETALLE, ID_TRANSACCION, FECHA_TRANSACCION, CONCEPTO, SUB_TOTAL FROM V_LISTAR_FIDE_DETALLE_TRANSACCION_TB ORDER BY ID_DETALLE DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DetalleTransaccionDTO dto = new DetalleTransaccionDTO();
            dto.setIdDetalle(rs.getLong("ID_DETALLE"));
            dto.setTransaccionId(rs.getLong("ID_TRANSACCION"));
            dto.setFechaTransaccion(rs.getDate("FECHA_TRANSACCION"));
            dto.setConcepto(rs.getString("CONCEPTO"));
            dto.setSubTotal(rs.getBigDecimal("SUB_TOTAL"));
            return dto;
        });
    }

}
