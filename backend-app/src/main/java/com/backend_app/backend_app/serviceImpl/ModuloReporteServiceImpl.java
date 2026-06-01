package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.ModuloReporteRepository;
import com.backend_app.backend_app.domain.ModuloReporte;
import com.backend_app.backend_app.dto.ModuloReporteDTO;
import com.backend_app.backend_app.functions.ModuloReporteFunctionRepository;
import com.backend_app.backend_app.repository.ModuloReporteStoredProcedureRepository;
import com.backend_app.backend_app.service.ModuloReporteService;
import com.backend_app.backend_app.views.ModuloViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModuloReporteServiceImpl implements ModuloReporteService {

    @Autowired
    private ModuloReporteRepository moduloReporteRepository;

    @Autowired
    private ModuloReporteStoredProcedureRepository moduloReporteStoredProcedureRepository;

    @Autowired
    private ModuloReporteFunctionRepository moduloReporteFunctionRepository;

    @Autowired
    private ModuloViewRepository moduloViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ModuloReporteDTO> listadoModuloReportes() {
        List<ModuloReporte> moduloReportes = moduloReporteRepository.findAll();
        List<ModuloReporteDTO> dtos = moduloReportes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModuloReporteDTO> buscarModuloReportePorId(long idModulo) {
        return moduloReporteRepository.findById(idModulo).map(this::convertirADTO);
    }

    @Override
    public ModuloReporteDTO convertirADTO(ModuloReporte moduloReporte) {
        ModuloReporteDTO dto = new ModuloReporteDTO();
        dto.setIdModulo(moduloReporte.getIdModulo());
        dto.setNombre(moduloReporte.getNombre());
        dto.setDescripcion(moduloReporte.getDescripcion());
        if (moduloReporte.getEstado() != null){
            dto.setEstadoId(moduloReporte.getEstado().getIdEstado());
            dto.setNombreEstado(moduloReporte.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void guardarModuloReporte(ModuloReporteDTO moduloReporteDTO) {
        moduloReporteStoredProcedureRepository.insertarModuloReporte(moduloReporteDTO);
    }

    @Override
    @Transactional
    public void actualizarModuloReporte(ModuloReporteDTO moduloReporteDTO) {
        moduloReporteStoredProcedureRepository.editarModuloReporte(moduloReporteDTO);
    }

    @Override
    @Transactional
    public void eliminarModuloReporte(ModuloReporteDTO moduloReporteDTO) {
        moduloReporteStoredProcedureRepository.elimminarModuloReporte(moduloReporteDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModuloReporteDTO> findAll(Pageable pageable) {
        return moduloReporteRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuloReporteDTO> buscarModuloReportePorNombre(String nombreModuloReporte) {
        return moduloReporteFunctionRepository.buscarModuloReportePorNombre(nombreModuloReporte);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuloReporteDTO> listadoModulosReportesCompletos() {
        return moduloViewRepository.getModulosReportesCompletos();
    }
}
