package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.ResultadosReporte;
import com.backend_app.backend_app.dto.ResultadosReporteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ResultadosReporteService {

    List<ResultadosReporteDTO> listadoResultadosReporte();

    Optional<ResultadosReporteDTO> buscarResultadosReportePorId(long idResultado);

    ResultadosReporteDTO convertirADTO(ResultadosReporte resultadosReporte);

    void insertarResultado(ResultadosReporteDTO resultadosReporteDTO);

    void actualizarResultado(ResultadosReporteDTO resultadosReporteDTO);

    Page<ResultadosReporteDTO> findAll(Pageable pageable);

    List<ResultadosReporteDTO> listadoResultadosReportesCompletos();

}
