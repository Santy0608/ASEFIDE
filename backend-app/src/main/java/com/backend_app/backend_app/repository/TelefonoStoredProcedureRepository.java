package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.domain.Telefono;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.TelefonoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TelefonoStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TelefonoStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertarTelefono(TelefonoDTO telefono) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_NUMERO_TELEFONO_INSERTAR_SP");

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NUMERO_TELEFONO", telefono.getNumeroTelefono());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
        System.out.println("Ejecución del procedimiento finalizada.");
    }


    public void editarTelefono(TelefonoDTO telefono) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_NUMERO_TELEFONO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_NUMERO", telefono.getIdTelefono()) // Identificador clave
                .addValue("P_NUMERO_TELEFONO", telefono.getNumeroTelefono())
                .addValue("P_ID_ESTADO", 1);
        jdbcCall.execute(in);
    }

    public void eliminarTelefono(TelefonoDTO telefono){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_NUMERO_TELEFONO_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_NUMERO", telefono.getIdTelefono());
        jdbcCall.execute(in);
    }


}
