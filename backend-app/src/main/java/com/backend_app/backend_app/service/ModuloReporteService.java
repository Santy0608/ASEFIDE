package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.ModuloReporte;
import com.backend_app.backend_app.dto.ModuloReporteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ModuloReporteService {

    List<ModuloReporteDTO> listadoModuloReportes();

    Optional<ModuloReporteDTO> buscarModuloReportePorId(long idModulo);

    ModuloReporteDTO convertirADTO(ModuloReporte moduloReporte);

    void guardarModuloReporte(ModuloReporteDTO moduloReporteDTO);

    void actualizarModuloReporte(ModuloReporteDTO moduloReporteDTO);

    void eliminarModuloReporte(ModuloReporteDTO moduloReporteDTO);

    Page<ModuloReporteDTO> findAll(Pageable pageable);

    List<ModuloReporteDTO> buscarModuloReportePorNombre(String nombreModuloReporte);

    List<ModuloReporteDTO> listadoModulosReportesCompletos();

}
