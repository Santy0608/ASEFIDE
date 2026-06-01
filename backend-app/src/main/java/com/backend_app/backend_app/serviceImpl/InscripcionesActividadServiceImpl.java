package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.InscripcionesActividadRepository;
import com.backend_app.backend_app.domain.InscripcionesActividad;
import com.backend_app.backend_app.dto.InscripcionesActividadDTO;
import com.backend_app.backend_app.repository.InscripcionesActividadStoredProcedureRepository;
import com.backend_app.backend_app.service.InscripcionesActividadService;
import com.backend_app.backend_app.views.InscripcionesActividadViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InscripcionesActividadServiceImpl implements InscripcionesActividadService {

    @Autowired
    private InscripcionesActividadRepository inscripcionesActividadRepository;

    @Autowired
    private InscripcionesActividadStoredProcedureRepository inscripcionesActividadStoredProcedureRepository;

    @Autowired
    private InscripcionesActividadViewRepository inscripcionesActividadViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionesActividadDTO> listadoInscripcionesActividad() {
        List<InscripcionesActividad> inscripcionesActividades = inscripcionesActividadRepository.findAll();
        List<InscripcionesActividadDTO> dtos = inscripcionesActividades.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InscripcionesActividadDTO> buscarInscripcionActividadPorId(long idInscripcion) {
        return inscripcionesActividadRepository.findById(idInscripcion).map(this::convertirADTO);
    }

    @Override
    public InscripcionesActividadDTO convertirADTO(InscripcionesActividad inscripcionesActividad) {
        InscripcionesActividadDTO dto = new InscripcionesActividadDTO();
        dto.setIdInscripcion(inscripcionesActividad.getIdInscripcion());
        if (inscripcionesActividad.getActividad().getIdActividad() != null){
            dto.setActividadId(inscripcionesActividad.getActividad().getIdActividad());
            dto.setNombreActividad(inscripcionesActividad.getActividad().getNombre());
        }
        if (inscripcionesActividad.getUsuario().getIdUsuario() != null){
            dto.setUsuarioId(inscripcionesActividad.getUsuario().getIdUsuario());
            dto.setIdentificacion(inscripcionesActividad.getUsuario().getIdentificacion());
            dto.setNombreUsuario(inscripcionesActividad.getUsuario().getNombreUsuario());
            dto.setApellidoPaterno(inscripcionesActividad.getUsuario().getApellidoPaterno());
        }
        dto.setFechaInscripcion(inscripcionesActividad.getFechaInscripcion());
        dto.setAsistenciaConfirmada(inscripcionesActividad.isAsistenciaConfirmada());
        if (inscripcionesActividad.getEstado().getIdEstado() != null){
            dto.setEstadoId(inscripcionesActividad.getEstado().getIdEstado());
            dto.setNombreEstado(inscripcionesActividad.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    public void agregarInscripcionActividad(InscripcionesActividadDTO inscripcionesActividadDTO) {
        inscripcionesActividadStoredProcedureRepository.insertarInscripcion(inscripcionesActividadDTO);
    }

    @Override
    @Transactional
    public void actualizarInscripcionActividad(InscripcionesActividadDTO inscripcionesActividadDTO) {
        inscripcionesActividadStoredProcedureRepository.editarInscripcionActividad(inscripcionesActividadDTO);
    }

    @Override
    @Transactional
    public void eliminarInscripcionActividad(InscripcionesActividadDTO inscripcionesActividadDTO) {
        inscripcionesActividadStoredProcedureRepository.eliminarInscripcionActividad(inscripcionesActividadDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InscripcionesActividadDTO> findAll(Pageable pageable) {
        return inscripcionesActividadRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionesActividadDTO> listadoInscripcionesActividadesCompletas() {
        return inscripcionesActividadViewRepository.getActividadesCompletas();
    }
}
