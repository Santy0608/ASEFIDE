package com.backend_app.backend_app.repository;

import com.backend_app.backend_app.dto.BeneficioDTO;
import com.backend_app.backend_app.dto.ServicioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ServicioStoredProcedureRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ServicioStoredProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertarServicio(ServicioDTO servicio) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG") // Nombre del paquete
                .withProcedureName("FIDE_SERVICIO_INSERTAR_SP");

        // Mapeo de parámetros
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE_SERVICIO", servicio.getNombreServicio());
        inParams.put("P_DESCRIPCION", servicio.getDescripcion());
        inParams.put("P_VALOR_ESTIMADO", servicio.getValorEstimado());
        inParams.put("P_STOCK", servicio.getStock());
        inParams.put("P_ID_CATEGORIA", servicio.getCategoriaId());
        inParams.put("P_ID_ESTADO", 1);
        jdbcCall.execute(inParams);
    }


    public void editarBeneficio(ServicioDTO servicio) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_SERVICIO_EDITAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_SERVICIO", servicio.getIdServicio()) // Identificador clave
                .addValue("P_NOMBRE_SERVICIO", servicio.getNombreServicio())
                .addValue("P_DESCRIPCION", servicio.getDescripcion())
                .addValue("P_VALOR_ESTIMADO", servicio.getValorEstimado())
                .addValue("P_STOCK", servicio.getStock())
                .addValue("P_ID_CATEGORIA", servicio.getCategoriaId())
                .addValue("P_ID_ESTADO", servicio.getEstadoId());

        jdbcCall.execute(in);
    }

    public void eliminarBeneficio(ServicioDTO servicio){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("ASEFIDE_PKG")
                .withProcedureName("FIDE_SERVICIO_ELIMINAR_SP");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_ID_SERVICIO", servicio.getIdServicio());
        jdbcCall.execute(in);
    }


}
