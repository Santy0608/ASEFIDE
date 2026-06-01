package com.backend_app.backend_app.views;

import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import com.backend_app.backend_app.dto.TransaccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CuentasAhorroViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CuentasAhorroViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CuentasAhorroDTO> getCuentasAhorrosCompletos() {
        String sql = "SELECT ID_AHORRO, ID_USUARIO, NOMBRE_USUARIO, MONTO_APORTE, FECHA_APERTURA, TIPO_AHORRO, SALDO_ACTUAL, ESTADO FROM V_LISTAR_FIDE_CUENTAS_AHORRO_TB ORDER BY ID_AHORRO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CuentasAhorroDTO dto = new CuentasAhorroDTO();
            dto.setIdAhorro(rs.getLong("ID_AHORRO"));
            dto.setUsuarioId(rs.getLong("ID_USUARIO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setMontoAporte(rs.getBigDecimal("MONTO_APORTE"));
            dto.setFechaApertura(rs.getDate("FECHA_APERTURA"));
            dto.setNombreTipoAhorro(rs.getString("TIPO_AHORRO"));
            dto.setSaldoActual(rs.getBigDecimal("SALDO_ACTUAL"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

}
