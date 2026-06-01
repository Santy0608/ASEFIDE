package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.domain.TipoAhorro;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TipoAhorroStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoAhorroStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarTipoAhorro(TipoAhorroDTO tipoAhorro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_AHORRO_INSERTAR_SP"); // Nombre del SP

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE", tipoAhorro.getNombre());
        inParams.put("P_DESCRIPCION", tipoAhorro.getDescripcion());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarTipoAhorro(TipoAhorroDTO tipoAhorro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_AHORRO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TIPO_AHORRO", tipoAhorro.getIdTipoAhorro()) // Identificador clave
                .addValue("P_NOMBRE", tipoAhorro.getNombre())
                .addValue("P_DESCRIPCION", tipoAhorro.getDescripcion())
                .addValue("P_ID_ESTADO", tipoAhorro.getIdTipoAhorro());
        jdbcCall.execute(in);
    }

    public void eliminarTipoAhorro(TipoAhorroDTO tipoAhorro){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_TIPO_AHORRO_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_TIPO_AHORRO", tipoAhorro.getIdTipoAhorro());
        jdbcCall.execute(in);
    }

}
