package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.TipoReporte;
import com.backend_app.backend_app.dto.TipoReporteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TipoReporteService {

    List<TipoReporteDTO> listadoTiposReportes();

    Optional<TipoReporteDTO> buscarTipoReportePorId(long idTipoReporte);

    TipoReporteDTO convertirADTO(TipoReporte tipoReporte);

    void insertarTipoReporte(TipoReporteDTO tipoReporteDTO);

    void actualizarTipoReporte(TipoReporteDTO tipoReporteDTO);

    void eliminarTipoReporte(TipoReporteDTO tipoReporteDTO);

    Page<TipoReporteDTO> findAll(Pageable pageable);

    List<TipoReporteDTO> buscarTipoReporte(String nombreTipoReporte);

    List<TipoReporteDTO> listadoTiposReportesCompletos();

}
