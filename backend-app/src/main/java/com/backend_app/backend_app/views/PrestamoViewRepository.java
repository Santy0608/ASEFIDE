package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.dto.PrestamoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrestamoViewRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PrestamoViewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EstadoDTO> getEstadoPrestamos() {
        String sql = "SELECT ID_PRESTAMO, CLIENTE, MONTO_SOLICITADO, SALDO_PENDIENTE, TASA_INTERESES, PLAZO_MESES, FECHA_APROBACION, ESTADO_PRESTAMO FROM V_FIDE_ESTADO_PRESTAMOS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            EstadoDTO dto = new EstadoDTO();
            dto.setPrestamoId(rs.getInt("ID_PRESTAMO"));
            dto.setCliente(rs.getString("CLIENTE"));
            dto.setMontoSolicitado(rs.getBigDecimal("MONTO_SOLICITADO"));
            dto.setSaldoPendiente(rs.getBigDecimal("SALDO_PENDIENTE"));
            dto.setTasaIntereses(rs.getDouble("TASA_INTERESES"));
            dto.setPlazoMeses(rs.getInt("PLAZO_MESES"));
            dto.setFechaAprobacion(rs.getDate("FECHA_APROBACION"));
            dto.setEstadoPrestamo(rs.getString("ESTADO_PRESTAMO"));
            return dto;
        });
    }

    public List<PrestamoDTO> getPrestamosCompletos(){
        String sql = "SELECT ID_PRESTAMO, ID_USUARIO, NOMBRE_USUARIO, MONTO_SOLICITADO, FECHA_APROBACION, SALDO_PENDIENTE, TASA_INTERESES, PLAZO_MESES , ESTADO FROM V_LISTAR_FIDE_PRESTAMO_TB ORDER BY ID_PRESTAMO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PrestamoDTO dto = new PrestamoDTO();
            dto.setIdPrestamo(rs.getLong("ID_PRESTAMO"));
            dto.setUsuarioId(rs.getLong("ID_USUARIO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setMontoSolicitado(rs.getBigDecimal("MONTO_SOLICITADO"));
            dto.setSaldoPendiente(rs.getBigDecimal("SALDO_PENDIENTE"));
            dto.setTasaIntereses(rs.getDouble("TASA_INTERESES"));
            dto.setPlazoMeses(rs.getInt("PLAZO_MESES"));
            dto.setFechaAprobacion(rs.getDate("FECHA_APROBACION"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }


}
