package com.backend_app.backend_app.views;

import com.backend_app.backend_app.domain.Prestamo;
import com.backend_app.backend_app.domain.Usuario;
import com.backend_app.backend_app.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioViewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UsuarioDTO> getUsuariosInactivos(){
        String sql = "SELECT ID_USUARIO, NOMBRE, ESTADO FROM V_FIDE_USUARIOS_INACTIVOS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(rs.getLong("ID_USUARIO"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

    public List<UsuarioDTO> getUsuariosCompletos() {
        String sql = "SELECT ID_USUARIO, IDENTIFICACION, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_USUARIO, ESTADO_USUARIO, FECHA_AFILIACION FROM V_FIDE_USUARIOS_COMPLETOS ORDER BY ID_USUARIO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(rs.getLong("ID_USUARIO"));
            dto.setIdentificacion(rs.getString("IDENTIFICACION"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
            dto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setEstadoUsuario(rs.getString("ESTADO_USUARIO"));
            dto.setFechaAfiliacion(rs.getDate("FECHA_AFILIACION"));
            return dto;
        });
    }

    public List<UsuarioDTO> getUsuariosUltimoMes() {
        String sql = "SELECT ID_USUARIO NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_USUARIO, FECHA_AFILIACION FROM FIDE_USUARIOS_ULTIMO_MES_VM";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(rs.getLong("ID_USUARIO"));
            dto.setNombre(rs.getString("NOMBRE"));
            dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
            dto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setFechaAfiliacion(rs.getDate("FECHA_AFILIACION"));
            return dto;
        });
    }

    public void refreshVistasMaterializadas() {
        jdbcTemplate.execute(
                "BEGIN DBMS_MVIEW.REFRESH('FIDE_USUARIOS_ULTIMO_MES_VM', 'C'); END;"
        );
    }

    public List<AporteDTO> getAportesPorUsuario(String nombreUsuario) {
        String sql = """
        SELECT ID_USUARIO, NOMBRE_COMPLETO, NOMBRE_USUARIO,
               CANTIDAD_APORTES, TOTAL_APORTES, APORTE_VIGENTE,
               PUESTO_EMPRESA, FECHA_AFILIACION, ESTADO_USUARIO
        FROM V_FIDE_APORTES_USUARIO
        WHERE NOMBRE_USUARIO = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{nombreUsuario}, (rs, rowNum) -> {
            AporteDTO dto = new AporteDTO();
            dto.setIdUsuario(rs.getLong("ID_USUARIO"));
            dto.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setCantidadAportes(rs.getInt("CANTIDAD_APORTES"));
            dto.setTotalAportes(rs.getBigDecimal("TOTAL_APORTES"));
            dto.setAporteVigente(rs.getBigDecimal("APORTE_VIGENTE"));
            dto.setFechaAfiliacion(rs.getDate("FECHA_AFILIACION"));
            dto.setEstadoUsuario(rs.getString("ESTADO_USUARIO"));
            return dto;
        });
    }

    public List<AporteDTO> getAportesPorUsuarioAdmin() {
        String sql = """
        SELECT ID_USUARIO, NOMBRE_COMPLETO, NOMBRE_USUARIO,
               CANTIDAD_APORTES, TOTAL_APORTES, APORTE_VIGENTE,
               FECHA_AFILIACION, ESTADO_USUARIO
        FROM FIDE_USUARIOS_VIEW
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AporteDTO dto = new AporteDTO();
            dto.setIdUsuario(rs.getLong("ID_USUARIO"));
            dto.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setCantidadAportes(rs.getInt("CANTIDAD_APORTES"));
            dto.setTotalAportes(rs.getBigDecimal("TOTAL_APORTES"));
            dto.setAporteVigente(rs.getBigDecimal("APORTE_VIGENTE"));
            dto.setFechaAfiliacion(rs.getDate("FECHA_AFILIACION"));
            dto.setEstadoUsuario(rs.getString("ESTADO_USUARIO"));
            return dto;
        });
    }

    public List<AporteDTO> getAportesPorUsuarioLogueado(String nombreUsuario) {
        String sql = """
        SELECT A.ID_APORTE, A.MONTO, A.FECHA_INICIO, A.FECHA_FIN
        FROM FIDE_APORTE_TB A
        JOIN FIDE_DATOS_ASOCIADOS_TB DA ON A.ID_DATOS_ASOCIADOS = DA.ID_DATOS_ASOCIADOS
        JOIN FIDE_USUARIO_TB U ON DA.ID_DATOS_ASOCIADOS = U.ID_DATOS_ASOCIADOS
        WHERE U.NOMBRE_USUARIO = ?
        ORDER BY A.FECHA_INICIO DESC
    """;

        return jdbcTemplate.query(sql, new Object[]{nombreUsuario}, (rs, rowNum) -> {
            AporteDTO dto = new AporteDTO();
            dto.setIdAporte(rs.getLong("ID_APORTE"));
            dto.setMonto(rs.getBigDecimal("MONTO"));
            dto.setFechaInicio(rs.getDate("FECHA_INICIO"));
            dto.setFechaFin(rs.getDate("FECHA_FIN"));
            return dto;
        });
    }



    public List<AhorroUsuarioDTO> getAhorrosPorUsuario(String nombreUsuario){
        String sql = """
                    SELECT ID_USUARIO, NOMBRE_COMPLETO, NOMBRE_USUARIO,
                           ID_AHORRO, FECHA_APERTURA, MONTO_APORTE, SALDO_ACTUAL, TIPO_AHORRO, ESTADO_CUENTA
                    FROM V_FIDE_AHORROS_USUARIO
                    WHERE NOMBRE_USUARIO = ?;
                """;
        return jdbcTemplate.query(sql, new Object[]{nombreUsuario}, (rs, rowNum) -> {
            AhorroUsuarioDTO dto = new AhorroUsuarioDTO();
            dto.setIdUsuario(rs.getLong("ID_USUARIO"));
            dto.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setIdAhorro(rs.getLong("ID_AHORRO"));
            dto.setFechaApertura(rs.getDate("FECHA_APERTURA"));
            dto.setMontoAporte(rs.getBigDecimal("MONTO_APORTE"));
            dto.setSaldoActual(rs.getBigDecimal("SALDO_ACTUAL"));
            dto.setTipoAhorro(rs.getString("TIPO_AHORRO"));
            dto.setEstadoCuenta(rs.getString("ESTADO_CUENTA"));
            return dto;
        });
    }

    public List<TransaccionUsuarioDTO> getTransaccionesPorUsuario(String nombreUsuario){
        String sql = """
                    SELECT NOMBRE_USUARIO, TIPO_TRANSACCION, MONTO,
                           FECHA_TRANSACCION, DESCRIPCION
                    FROM V_FIDE_MIS_TRANSACCIONES
                    WHERE NOMBRE_USUARIO = ?
                """;
        return jdbcTemplate.query(sql, new Object[]{nombreUsuario}, (rs, rowNum) -> {
            TransaccionUsuarioDTO dto = new TransaccionUsuarioDTO();
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setTipoTransaccion(rs.getString("TIPO_TRANSACCION"));
            dto.setMonto(rs.getBigDecimal("MONTO"));
            dto.setFechaTransaccion(rs.getDate("FECHA_TRANSACCION"));
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            return dto;
        });
    }

    public List<PrestamoUsuarioDTO> getPrestamoUsuarioDTO(String nombreUsuario){
        String sql = """
                    SELECT NOMBRE_USUARIO, MONTO_SOLICITADO, SALDO_PENDIENTE, 
                           CUOTAS_PAGADAS, PROXIMA_FECHA_PAGO, ESTADO 
                    FROM V_FIDE_MIS_PRESTAMOS 
                    WHERE NOMBRE_USUARIO = ?;
                """;
        return jdbcTemplate.query(sql, new Object[]{nombreUsuario}, (rs, rowNum) -> {
            PrestamoUsuarioDTO dto = new PrestamoUsuarioDTO();
            dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
            dto.setMontoSolicitado(rs.getBigDecimal("MONTO_SOLICITADO"));
            dto.setSaldoPendiente(rs.getBigDecimal("SALDO_PENDIENTE"));
            dto.setCuotasPagadas(rs.getInt("CUOTAS_PAGADAS"));
            dto.setProximaFechaPago(rs.getDate("PROXIMA_FECHA_PAGO"));
            dto.setEstado(rs.getString("ESTADO"));
            return dto;
        });
    }

    public List<InscripcionUsuarioDTO> getInscripcionesActividadPorUsuario(String nombreUsuario){
        String sql = """
                    SELECT NOMBRE_ACTIVIDAD, FECHA_EVENTO, NOMBRE_LUGAR, ESTADO_INSCRIPCION
                    FROM V_FIDE_MIS_INSCRIPCIONES
                    WHERE NOMBRE_USUARIO = ?;
                """;
        return jdbcTemplate.query(sql, new Object[]{nombreUsuario}, (rs, rowNum) -> {
            InscripcionUsuarioDTO dto = new InscripcionUsuarioDTO();
            dto.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
            dto.setFechaEvento(rs.getString("FECHA_EVENTO"));
            dto.setNombreLugar(rs.getString("NOMBRE_LUGAR"));
            dto.setEstadoInscripcion(rs.getString("ESTADO_INSCRIPCION"));
            return dto;
        });
    }

}
