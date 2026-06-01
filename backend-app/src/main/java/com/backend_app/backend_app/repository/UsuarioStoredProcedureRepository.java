package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.SqlArrayValue;
import com.backend_app.backend_app.dto.UsuarioDTO;
import com.backend_app.backend_app.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UsuarioStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertarUsuario(UserRequest usuario) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_USUARIOS_INSERTAR_SP")
                .declareParameters(
                        new SqlParameter("P_IDENTIFICACION", Types.VARCHAR),
                        new SqlParameter("P_NOMBRE", Types.VARCHAR),
                        new SqlParameter("P_APELLIDO_PATERNO", Types.VARCHAR),
                        new SqlParameter("P_APELLIDO_MATERNO", Types.VARCHAR),
                        new SqlParameter("P_ID_DATOS_ASOCIADOS", Types.NUMERIC),
                        new SqlParameter("P_ID_ESTADO", Types.NUMERIC),
                        new SqlParameter("P_ID_DIRECCION", Types.NUMERIC),
                        new SqlParameter("P_NOMBRE_USUARIO", Types.VARCHAR),
                        new SqlParameter("P_CONTRASENIA", Types.VARCHAR),
                        new SqlParameter("P_CORREOS_IDS", Types.ARRAY, "SYS.ODCINUMBERLIST"), // ← cambio
                        new SqlParameter("P_NUMEROS_IDS", Types.ARRAY,"SYS.ODCINUMBERLIST"),

                        new SqlOutParameter("P_ID_USUARIO", Types.NUMERIC)
                );
        Long[] correosArray = usuario.getCorreosIds().toArray(new Long[0]);
        Long[] numerosArray = usuario.getNumerosIds().toArray(new Long[0]);

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_IDENTIFICACION", usuario.getIdentificacion());
        inParams.put("P_NOMBRE", usuario.getNombre());
        inParams.put("P_APELLIDO_PATERNO", usuario.getApellidoPaterno());
        inParams.put("P_APELLIDO_MATERNO", usuario.getApellidoMaterno());
        inParams.put("P_ID_DATOS_ASOCIADOS", usuario.getIdentificacionDatosAsociados());
        inParams.put("P_ID_ESTADO", 1);
        inParams.put("P_ID_DIRECCION", usuario.getDireccionId());
        inParams.put("P_NOMBRE_USUARIO", usuario.getNombreUsuario());
        inParams.put("P_CONTRASENIA", usuario.getContrasenia());
        inParams.put("P_CORREOS_IDS", new SqlArrayValue(correosArray, "SYS.ODCINUMBERLIST"));
        inParams.put("P_NUMEROS_IDS", new SqlArrayValue(numerosArray, "SYS.ODCINUMBERLIST"));

        Map<String, Object> result = jdbcCall.execute(inParams);

        BigDecimal id = (BigDecimal) result.get("P_ID_USUARIO");

        return id.longValue();
    }



    public void editarUsuario(UserRequest usuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_USUARIOS_EDITAR_SP");

        Long[] correosArray = usuario.getCorreosIds() != null ?
                usuario.getCorreosIds().toArray(new Long[0]) : new Long[0];
        Long[] numerosArray = usuario.getNumerosIds() != null ?
                usuario.getNumerosIds().toArray(new Long[0]) : new Long[0];

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_USUARIO", usuario.getIdUsuario()) // Identificador clave
                .addValue("P_IDENTIFICACION", usuario.getIdentificacion())
                .addValue("P_NOMBRE", usuario.getNombre())
                .addValue("P_APELLIDO_PATERNO", usuario.getApellidoPaterno())
                .addValue("P_APELLIDO_MATERNO", usuario.getApellidoMaterno())
                .addValue("P_ID_NUMERO", usuario.getTelefonoId())
                .addValue("P_ID_DATOS_ASOCIADOS", usuario.getIdentificacionDatosAsociados())
                .addValue("P_ID_ESTADO", usuario.getEstadoId())
                .addValue("P_ID_DIRECCION", usuario.getDireccionId())
                .addValue("P_NOMBRE_USUARIO", usuario.getNombreUsuario())
                .addValue("P_ID_CORREO", usuario.getCorreoId())

                .addValue("P_CORREOS_IDS", new SqlArrayValue(correosArray, "SYS.ODCINUMBERLIST"))
                .addValue("P_NUMEROS_IDS", new SqlArrayValue(numerosArray, "SYS.ODCINUMBERLIST"));
        jdbcCall.execute(in);
    }

    public void eliminarUsuario(UsuarioDTO usuario){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_USUARIOS_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_USUARIO", usuario.getIdUsuario());
        jdbcCall.execute(in);
    }


}
