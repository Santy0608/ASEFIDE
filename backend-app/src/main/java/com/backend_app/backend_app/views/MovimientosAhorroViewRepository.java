package com.backend_app.backend_app.views;

import com.backend_app.backend_app.domain.MovimientosAhorro;
import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import com.backend_app.backend_app.dto.MovimientosAhorroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovimientosAhorroViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovimientosAhorroViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MovimientosAhorroDTO> getMovimientosAhorrosCompletos() {
        String sql = "SELECT ID_MOVIMIENTO, ID_AHORRO, NOMBRE_USUARIO, ID_TRANSACCION, FECHA_TRANSACCION, MONTO, FECHA_DEPOSITO FROM V_LISTAR_FIDE_MOVIMIENTOS_AHORRO_TB ORDER BY ID_MOVIMIENTO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MovimientosAhorroDTO dto = new MovimientosAhorroDTO();
            dto.setIdMovimiento(rs.getLong("ID_MOVIMIENTO"));
            dto.setCuentasAhorroId(rs.getLong("ID_AHORRO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setTransaccionId(rs.getLong("ID_TRANSACCION"));
            dto.setFechaTransaccion(rs.getDate("FECHA_TRANSACCION"));
            dto.setMonto(rs.getBigDecimal("MONTO"));
            dto.setFechaDeposito(rs.getDate("FECHA_DEPOSITO"));
            return dto;
        });
    }


}
