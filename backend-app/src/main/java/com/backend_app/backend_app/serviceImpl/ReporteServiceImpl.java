package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.ReporteRepository;
import com.backend_app.backend_app.domain.Reporte;
import com.backend_app.backend_app.dto.ReporteDTO;
import com.backend_app.backend_app.repository.ReporteStoredProcedureRepository;
import com.backend_app.backend_app.service.ReporteService;
import com.backend_app.backend_app.views.ReporteViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private ReporteStoredProcedureRepository reporteStoredProcedureRepository;

    @Autowired
    private ReporteViewRepository reporteViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> listadoReportes() {
        List<Reporte> reportes = reporteRepository.findAll();
        List<ReporteDTO> dtos = reportes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReporteDTO> buscarReportePorId(long idReporte) {
        return reporteRepository.findById(idReporte).map(this::convertirADTO);
    }

    @Override
    public ReporteDTO convertirADTO(Reporte reporte) {
        ReporteDTO dto = new ReporteDTO();
        dto.setIdReporte(reporte.getIdReporte());
        if (reporte.getTipoReporte() != null){
            dto.setTipoReporteId(reporte.getTipoReporte().getIdTipoReporte());
            dto.setNombreTipoReporte(reporte.getTipoReporte().getNombre());
        }
        dto.setFechaInicio(reporte.getFechaInicio());
        dto.setFechaFinal(reporte.getFechaFinal());
        dto.setTotalRegistros(reporte.getTotalRegistros());
        dto.setResumenMontos(reporte.getResumenMontos());
        dto.setFechaGeneracion(reporte.getFechaGeneracion());
        if (reporte.getModuloReporte() != null){
            dto.setIdModuloReporte(reporte.getModuloReporte().getIdModulo());
            dto.setNombreModuloReporte(reporte.getModuloReporte().getNombre());
        }
        if (reporte.getEstado() != null) {
            dto.setEstadoId(reporte.getEstado().getIdEstado());
            dto.setNombreEstado(reporte.getEstado().getNombre());
        }
        if (reporte.getUsuario() != null){
            dto.setUsuarioId(reporte.getUsuario().getIdentificacion());
            dto.setNombreUsuario(reporte.getUsuario().getNombreUsuario());
            dto.setApellidoPaterno(reporte.getUsuario().getApellidoPaterno());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarReporte(ReporteDTO reporteDTO) {
        reporteStoredProcedureRepository.registrarReporte(reporteDTO);
    }

    @Override
    @Transactional
    public void actualizarReporte(ReporteDTO reporteDTO) {
        reporteStoredProcedureRepository.editarReporte(reporteDTO);
    }

    @Override
    @Transactional
    public void eliminarReporte(ReporteDTO reporteDTO) {
        reporteStoredProcedureRepository.eliminarReporte(reporteDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReporteDTO> findAll(Pageable pageable) {
        return reporteRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> listadoReportesCompletos() {
        return reporteViewRepository.getReportesCompletos();
    }
}
