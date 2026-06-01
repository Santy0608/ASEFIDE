package com.backend_app.backend_app.service;


import com.backend_app.backend_app.dao.ReporteRepository;
import com.backend_app.backend_app.domain.Reporte;
import com.backend_app.backend_app.dto.ReporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


public interface ReporteService {

    List<ReporteDTO> listadoReportes();

    Optional<ReporteDTO> buscarReportePorId(long idReporte);

    ReporteDTO convertirADTO(Reporte reporte);

    void insertarReporte(ReporteDTO reporteDTO);

    void actualizarReporte(ReporteDTO reporteDTO);

    void eliminarReporte(ReporteDTO reporteDTO);

    Page<ReporteDTO> findAll(Pageable pageable);

    List<ReporteDTO> listadoReportesCompletos();

}
