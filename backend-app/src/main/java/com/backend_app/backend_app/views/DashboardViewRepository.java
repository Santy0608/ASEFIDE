package com.backend_app.backend_app.views;


import com.backend_app.backend_app.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardViewRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DashboardViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public DashboardKpiDTO getDashboardKPIs() {

        String sql = "SELECT ASOCIADOS_ACTIVOS, SALDO_TOTAL_AHORROS, PRESTAMOS_PENDIENTES, TRANSACCIONES_MES FROM V_FIDE_DASHBOARD_KPIS";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            DashboardKpiDTO dto = new DashboardKpiDTO();

            dto.setTotalAsociadosActivos(rs.getInt("ASOCIADOS_ACTIVOS"));
            dto.setTotalAhorros(rs.getBigDecimal("SALDO_TOTAL_AHORROS"));
            dto.setTotalPrestamosPendientes(rs.getInt("PRESTAMOS_PENDIENTES"));
            dto.setTransaccionesMesActual(rs.getInt("TRANSACCIONES_MES"));

            return dto;
        });
    }

    public List<DashboardPrestamosEstadoDTO> getDashboardPrestamosEstado() {
        String sql = "SELECT ESTADO, TOTAL_PRESTAMOS, SALDO_PENDIENTE_TOTAL, SALDO_PROMEDIO, MONTO_SOLICITADO_TOTAL, PORCENTAJE_PRESTAMOS FROM V_FIDE_DASHBOARD_PRESTAMOS_ESTADO";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DashboardPrestamosEstadoDTO dto = new DashboardPrestamosEstadoDTO();
            dto.setEstado(rs.getString("ESTADO"));
            dto.setTotalPrestamos(rs.getInt("TOTAL_PRESTAMOS"));
            dto.setSaldoPendienteTotal(rs.getBigDecimal("SALDO_PENDIENTE_TOTAL"));
            dto.setSaldoPromedio(rs.getBigDecimal("SALDO_PROMEDIO"));
            dto.setMontoSolicitadoTotal(rs.getBigDecimal("MONTO_SOLICITADO_TOTAL"));
            dto.setPorcentajePrestamos(rs.getDouble("PORCENTAJE_PRESTAMOS"));
            return dto;
        });
    }

    public List<DashboardActividadesProximasDTO> getDashboardActividadesProximas(){
        String sql = "SELECT NOMBRE_ACTIVIDAD, LUGAR, TOTAL_INSCRITOS, CUPO_DISPONIBLE, PORCENTAJE_OCUPACION, DIAS_PARA_EVENTO FROM V_FIDE_DASHBOARD_ACTIVIDADES_PROXIMAS";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DashboardActividadesProximasDTO dto = new DashboardActividadesProximasDTO();
            dto.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
            dto.setLugar(rs.getString("LUGAR"));
            dto.setTotalInscritos(rs.getInt("TOTAL_INSCRITOS"));
            dto.setCupoDisponible(rs.getInt("CUPO_DISPONIBLE"));
            dto.setPorcentajeOcupacion(rs.getDouble("PORCENTAJE_OCUPACION"));
            dto.setDiasParaEvento(rs.getInt("DIAS_PARA_EVENTO"));
            return dto;
        });
    }

    public List<DashboardTransaccionDTO> getDashboardTransacciones() {
        String sql = "SELECT MES, TIPO, CANTIDAD, MONTO_TOTAL FROM FIDE_DASHBOARD_TRANSACCIONES_MES_VM ORDER BY MES_ORDEN ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DashboardTransaccionDTO dto = new DashboardTransaccionDTO();
            dto.setMes(rs.getString("MES"));
            dto.setTipo(rs.getString("TIPO"));
            dto.setCantidad(rs.getInt("CANTIDAD"));
            dto.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
            return dto;
        });
    }

    public List<DashboardTipoAhorroDTO> getResumenTiposAhorro() {
        String sql = "SELECT TIPO_AHORRO, TOTAL_CUENTAS, SALDO_TOTAL, SALDO_PROMEDIO, PORCENTAJE_CUENTAS " +
                "FROM FIDE_DASHBOARD_TIPOS_AHORRO_VM " +
                "ORDER BY SALDO_TOTAL DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DashboardTipoAhorroDTO dto = new DashboardTipoAhorroDTO();
            dto.setTipoAhorro(rs.getString("TIPO_AHORRO"));
            dto.setTotalCuentas(rs.getInt("TOTAL_CUENTAS"));
            dto.setSaldoTotal(rs.getBigDecimal("SALDO_TOTAL"));
            dto.setSaldoPromedio(rs.getBigDecimal("SALDO_PROMEDIO"));
            dto.setPorcentajeCuentas(rs.getDouble("PORCENTAJE_CUENTAS"));
            return dto;
        });
    }

}
