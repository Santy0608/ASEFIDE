package com.backend_app.backend_app.functions;

import com.backend_app.backend_app.domain.CuentasAhorro;
import com.backend_app.backend_app.domain.Usuario;
import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioFunctionRepository {

    public JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioFunctionRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //Llamando función para listar usuarios
    public Integer listarUsuarios(){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_LISTAR_USUARIOS_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                  new SqlOutParameter("RETURN", Types.NUMERIC)
                );

        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return ((Number) result.get("RETURN")).intValue();
    }

    public List<Map<String, Object>> buscarUsuariosPorNombre(String nombre) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_USUARIOS_NOMBRE_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR),
                        new SqlParameter("P_NOMBRE_BUSCAR", Types.VARCHAR)
                );

        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_BUSCAR", nombre);

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (List<Map<String, Object>>) result.get("RETURN");
    }



    public List<UsuarioDTO> ordenarAlfabetico(){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_ORDENAR_USUARIOS_ALFABETICO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    UsuarioDTO dto = new UsuarioDTO();
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                                    dto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
                                    return dto;
                                })
                );
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return (List<UsuarioDTO>) result.get("RETURN");
    }

    //  Ordenar Por Fecha
    public List<UsuarioDTO> ordenarPorFecha() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_ORDENAR_USUARIOS_FECHA_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    UsuarioDTO dto = new UsuarioDTO();
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                                    dto.setFechaAfiliacion(rs.getDate("FECHA_AFILIACION"));
                                    return dto;
                                })
                );
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return (List<UsuarioDTO>) result.get("RETURN");
    }



    // Buscar por nombre
    public List<UsuarioDTO> buscarPorNombre(String nombre){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_BUSCAR_USUARIOS_NOMBRE_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    UsuarioDTO dto = new UsuarioDTO();
                                    dto.setIdUsuario(rs.getLong("ID_USUARIO"));
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                                    dto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
                                    dto.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                                    return dto;
                                }),
                        new SqlParameter("P_NOMBRE_BUSCAR", Types.VARCHAR)
                );
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("P_NOMBRE_BUSCAR", nombre);
        Map<String, Object> result = jdbcCall.execute(inParams);
        return (List<UsuarioDTO>) result.get("RETURN");
    }

    public List<EstadoDTO> usuariosPorEstado() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_USUARIOS_POR_ESTADO_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    EstadoDTO dto = new EstadoDTO();
                                    dto.setNombre(rs.getString("NOMBRE_ESTADO"));
                                    dto.setCantidadUsuarios(rs.getInt("CANTIDAD_USUARIOS"));
                                    return dto;
                                })
                );
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return (List<EstadoDTO>) result.get("RETURN");
    }

    // Usuarios Último Mes
    public List<UsuarioDTO> usuariosUltimoMes() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withFunctionName("FIDE_USUARIOS_ULTIMO_MES_FN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("RETURN", OracleTypes.CURSOR,
                                (rs, rowNum) -> {
                                    UsuarioDTO dto = new UsuarioDTO();
                                    dto.setNombre(rs.getString("NOMBRE"));
                                    dto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                                    dto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
                                    dto.setFechaAfiliacion(rs.getDate("FECHA_AFILIACION"));
                                    return dto;
                                })
                );
        Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource());
        return (List<UsuarioDTO>) result.get("RETURN");
    }


}
