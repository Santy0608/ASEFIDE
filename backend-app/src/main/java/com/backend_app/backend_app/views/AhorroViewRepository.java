package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AhorroViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AhorroViewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CuentasAhorroDTO> getResumenAhorros() {
        String sql = "SELECT ID_AHORRO, NOMBRE_USUARIO, TIPO_AHORRO, MONTO_APORTE, SALDO_ACTUAL, FECHA_APERTURA, ESTADO_CUENTA FROM V_FIDE_RESUMEN_AHORROS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CuentasAhorroDTO dto = new CuentasAhorroDTO();
            dto.setIdAhorro(rs.getLong("ID_AHORRO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setNombreTipoAhorro(rs.getString("TIPO_AHORRO"));
            dto.setMontoAporte(rs.getBigDecimal("MONTO_APORTE"));
            dto.setSaldoActual(rs.getBigDecimal("SALDO_ACTUAL"));
            dto.setFechaApertura(rs.getDate("FECHA_APERTURA"));
            dto.setEstadoCuenta(rs.getString("ESTADO_CUENTA"));
            return dto;
        });
    }

    public List<CuentasAhorroDTO> getUsuariosMayorAhorro() {
        String sql = "SELECT ID_USUARIO, NOMBRE, APELLIDO_PATERNO, SALDO_ACTUAL FROM FIDE_USUARIOS_MAYOR_AHORRO_VM";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CuentasAhorroDTO dto = new CuentasAhorroDTO();
            dto.setUsuarioId(rs.getLong("ID_USUARIO"));
            dto.setNombreUsuario(rs.getString("NOMBRE"));
            dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
            dto.setSaldoActual(rs.getBigDecimal("SALDO_ACTUAL"));
            return dto;
        });
    }



}
