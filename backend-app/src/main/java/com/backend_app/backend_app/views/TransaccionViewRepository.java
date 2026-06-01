package com.backend_app.backend_app.views;

import com.backend_app.backend_app.domain.Transaccion;
import com.backend_app.backend_app.dto.TransaccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransaccionViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransaccionViewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TransaccionDTO> getTopUsuariosTransacciones() {
        String sql = "SELECT ID_USUARIO, NOMBRE, APELLIDO_PATERNO, TOTAL_TRANSACCIONES FROM FIDE_TOP_USUARIOS_TRANSACCIONES_VM";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TransaccionDTO dto = new TransaccionDTO();
            dto.setUsuarioId(rs.getLong("ID_USUARIO"));
            dto.setNombreUsuario(rs.getString("NOMBRE"));
            dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
            dto.setTotalTransacciones(rs.getInt("TOTAL_TRANSACCIONES"));
            return dto;
        });
    }

    public List<TransaccionDTO> getHistorialTransacciones() {
        String sql = "SELECT ID_TRANSACCION, FECHA_TRANSACCION, MONTO_TOTAL, ID_USUARIO, NOMBRE, APELLIDO_PATERNO FROM FIDE_HISTORIAL_TRANSACCIONES_VM";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TransaccionDTO dto = new TransaccionDTO();
            dto.setIdTransaccion(rs.getLong("ID_TRANSACCION"));
            dto.setFechaTransaccion(rs.getDate("FECHA_TRANSACCION"));
            dto.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
            dto.setUsuarioId(rs.getLong("ID_USUARIO"));
            dto.setNombreUsuario(rs.getString("NOMBRE"));
            dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
            return dto;
        });
    }

    public List<TransaccionDTO> getTransaccionesCompletas() {
        String sql = "SELECT ID_TRANSACCION, ID_USUARIO, FECHA_TRANSACCION, TIPO_TRANSACCION, MONTO_TOTAL, NOMBRE_USUARIO, ESTADO FROM V_LISTAR_FIDE_TRANSACCION_TB ORDER BY ID_TRANSACCION DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TransaccionDTO dto = new TransaccionDTO();
            dto.setIdTransaccion(rs.getLong("ID_TRANSACCION"));
            dto.setUsuarioId(rs.getLong("ID_USUARIO"));
            dto.setFechaTransaccion(rs.getDate("FECHA_TRANSACCION"));
            dto.setNombreTipoTransaccion(rs.getString("TIPO_TRANSACCION"));
            dto.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }


}
