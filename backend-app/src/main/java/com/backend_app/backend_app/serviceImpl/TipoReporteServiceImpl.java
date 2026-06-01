package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.TipoReporteRepository;
import com.backend_app.backend_app.domain.TipoReporte;
import com.backend_app.backend_app.dto.TipoReporteDTO;
import com.backend_app.backend_app.functions.TipoReporteFunctionRepository;
import com.backend_app.backend_app.repository.TipoReporteStoredProcedureRepository;
import com.backend_app.backend_app.service.TipoAhorroService;
import com.backend_app.backend_app.service.TipoReporteService;
import com.backend_app.backend_app.views.TipoReporteViewRepository;
import jakarta.validation.GroupSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoReporteServiceImpl implements TipoReporteService {

    @Autowired
    private TipoReporteRepository tipoReporteRepository;

    @Autowired
    private TipoReporteStoredProcedureRepository tipoReporteStoredProcedureRepository;

    @Autowired
    private TipoReporteFunctionRepository tipoReporteFunctionRepository;

    @Autowired
    private TipoReporteViewRepository tipoReporteViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoReporteDTO> listadoTiposReportes() {
        List<TipoReporte> tipoReportes = tipoReporteRepository.findAll();
        List<TipoReporteDTO> dtos = tipoReportes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoReporteDTO> buscarTipoReportePorId(long idTipoReporte) {
        return tipoReporteRepository.findById(idTipoReporte).map(this::convertirADTO);
    }

    @Override
    public TipoReporteDTO convertirADTO(TipoReporte tipoReporte) {
        TipoReporteDTO dto = new TipoReporteDTO();
        dto.setIdTipoReporte(tipoReporte.getIdTipoReporte());
        dto.setNombre(tipoReporte.getNombre());
        if (tipoReporte.getEstado() != null){
            dto.setEstadoId(tipoReporte.getEstado().getIdEstado());
            dto.setNombreEstado(tipoReporte.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarTipoReporte(TipoReporteDTO tipoReporteDTO) {
        tipoReporteStoredProcedureRepository.insertarTipoReporte(tipoReporteDTO);
    }

    @Override
    @Transactional
    public void actualizarTipoReporte(TipoReporteDTO tipoReporteDTO) {
        tipoReporteStoredProcedureRepository.editarTipoReporte(tipoReporteDTO);
    }

    @Override
    @Transactional
    public void eliminarTipoReporte(TipoReporteDTO tipoReporteDTO) {
        tipoReporteStoredProcedureRepository.eliminarTipoReporte(tipoReporteDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoReporteDTO> findAll(Pageable pageable) {
        return tipoReporteRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoReporteDTO> buscarTipoReporte(String nombreTipoReporte) {
        return tipoReporteFunctionRepository.buscarTipoReportePorNombre(nombreTipoReporte);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoReporteDTO> listadoTiposReportesCompletos() {
        return tipoReporteViewRepository.getTiposReportesCompletos();
    }

}
