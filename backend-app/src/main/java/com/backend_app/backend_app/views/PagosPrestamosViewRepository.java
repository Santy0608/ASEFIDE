package com.backend_app.backend_app.views;

import com.backend_app.backend_app.domain.PagosPrestamos;
import com.backend_app.backend_app.dto.PagosPrestamosDTO;
import com.backend_app.backend_app.dto.PrestamoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PagosPrestamosViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PagosPrestamosViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PagosPrestamosDTO> getPagosPrestamosCompletos(){
        String sql = "SELECT ID_PAGO, ID_TRANSACCION, FECHA_TRANSACCION, ID_PRESTAMO, NOMBRE_USUARIO, MONTO_ABONADO, FECHA_PAGO FROM V_LISTAR_FIDE_PAGOS_PRESTAMOS_TB ORDER BY ID_PAGO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PagosPrestamosDTO dto = new PagosPrestamosDTO();
            dto.setIdPago(rs.getLong("ID_PAGO"));
            dto.setTransaccionId(rs.getLong("ID_TRANSACCION"));
            dto.setFechaTransaccion(rs.getDate("FECHA_TRANSACCION"));
            dto.setPrestamoId(rs.getLong("ID_PRESTAMO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setMontoAbonado(rs.getDouble("MONTO_ABONADO"));
            dto.setFechaPago(rs.getDate("FECHA_PAGO"));
            return dto;
        });
    }


}
