package com.backend_app.backend_app.views;

import com.backend_app.backend_app.domain.PuestoEmpresa;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.PuestoEmpresaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PuestoEmpresaAsociadoViewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PuestoEmpresaAsociadoViewRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PuestoEmpresaDTO> getPuestosEmpresasCompletos() {
        String sql = "SELECT ID_PUESTO_EMPRESA, PUESTO_EMPRESA, ESTADO FROM V_LISTAR_FIDE_PUESTO_EMPRESA_ASOCIADO_TB ORDER BY ID_PUESTO_EMPRESA DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PuestoEmpresaDTO dto = new PuestoEmpresaDTO();
            dto.setIdPuestoEmpresa(rs.getLong("ID_PUESTO_EMPRESA"));
            dto.setPuestoEmpresa(rs.getString("PUESTO_EMPRESA"));
            dto.setNombreEstado(rs.getString("ESTADO"));
            return dto;
        });
    }


}
