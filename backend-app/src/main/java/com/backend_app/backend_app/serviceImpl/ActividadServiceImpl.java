package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.ActividadRepository;
import com.backend_app.backend_app.domain.Actividad;
import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.functions.ActividadFunctionRepository;
import com.backend_app.backend_app.repository.ActividadStoredProcedureRepository;
import com.backend_app.backend_app.service.ActividadService;
import com.backend_app.backend_app.views.ActividadViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ActividadStoredProcedureRepository actividadStoredProcedureRepository;

    @Autowired
    private ActividadViewRepository actividadViewRepository;

    @Autowired
    private ActividadFunctionRepository actividadFunctionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ActividadDTO> listadoActivides() {
        List<Actividad> actividades = actividadRepository.findAll();
        List<ActividadDTO> dtos = actividades.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActividadDTO> buscarActividadPorId(long idActividad) {
        return actividadRepository.findById(idActividad).map(this::convertirADTO);
    }

    @Override
    public ActividadDTO convertirADTO(Actividad actividad) {
        ActividadDTO dto = new ActividadDTO();
        dto.setIdActividad(actividad.getIdActividad());
        dto.setNombre(actividad.getNombre());
        dto.setDescripcion(actividad.getDescripcion());
        dto.setFechaEvento(actividad.getFechaEvento());

        if (actividad.getLugarEvento() != null){
            dto.setLugarEventoId(actividad.getLugarEvento().getIdLugarEvento());
            dto.setNombreLugarEvento(actividad.getLugarEvento().getNombreLugar());
        }

        if (actividad.getEstado() != null){
            dto.setEstadoId(actividad.getEstado().getIdEstado());
            dto.setNombreEstado(actividad.getEstado().getNombre());
        }

        if (actividad.getUsuario() != null){
            dto.setUsuarioId(actividad.getUsuario().getIdUsuario());
            dto.setNombreUsuario(actividad.getUsuario().getNombre());
            dto.setApellidoPaterno(actividad.getUsuario().getApellidoPaterno());
        }
        dto.setCupoTotal(actividad.getCupoTotal());
        dto.setImagenUrl(actividad.getImagenUrl());

        return dto;
    }

    @Override
    @Transactional
    public void insertarActividad(ActividadDTO actividadDTO) {
        actividadStoredProcedureRepository.insertarActividad(actividadDTO);
    }

    @Override
    @Transactional
    public void actualizarActividad(ActividadDTO actividadDTO) {
        actividadStoredProcedureRepository.editarActividad(actividadDTO);
    }

    @Override
    @Transactional
    public void elimminarActividad(ActividadDTO actividadDTO) {
        actividadStoredProcedureRepository.eliminarActividad(actividadDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActividadDTO> findAll(Pageable pageable) {
        return actividadRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadDTO> obtenerActividadesAsociados() {
        return actividadViewRepository.getActividadesAsociados();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadDTO> buscarActividadPorNombre(String nombre) {
        return actividadFunctionRepository.buscarActividadPorNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadDTO> listadoActividadesProgramadas() {
        return actividadViewRepository.getActividadesProgramadas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadDTO> listadoActividadesCompletas() {
        return actividadViewRepository.getActividadesCompletas();
    }

}
