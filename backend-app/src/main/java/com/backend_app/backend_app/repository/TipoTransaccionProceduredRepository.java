package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.dto.TipoTransaccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TipoTransaccionProceduredRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoTransaccionProceduredRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarTipoTransaccion(TipoTransaccionDTO tipoTransaccion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_TRANSACCION_INSERTAR_SP"); // Nombre del SP

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE", tipoTransaccion.getNombre());
        inParams.put("P_DESCRIPCION", tipoTransaccion.getDescripcion());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarTipoTransaccion(TipoTransaccionDTO tipoTransaccion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_TRANSACCION_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TIPO_TRANSACCION", tipoTransaccion.getIdTipoTransaccion()) // Identificador clave
                .addValue("P_NOMBRE", tipoTransaccion.getNombre())
                .addValue("P_DESCRIPCION", tipoTransaccion.getDescripcion())
                .addValue("P_ID_ESTADO", 1);
        jdbcCall.execute(in);
    }

    public void eliminarTipoTransaccion(TipoTransaccionDTO tipoTransaccion){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_TRANSACCION_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TIPO_TRANSACCION", tipoTransaccion.getIdTipoTransaccion());
        jdbcCall.execute(in);
    }

}
