package com.backend_app.backend_app.serviceImpl;


import com.backend_app.backend_app.dao.ServicioRepository;
import com.backend_app.backend_app.domain.Servicio;
import com.backend_app.backend_app.dto.ServicioDTO;
import com.backend_app.backend_app.functions.ServicioFunctionRepository;
import com.backend_app.backend_app.repository.ServicioStoredProcedureRepository;
import com.backend_app.backend_app.service.ServicioService;
import com.backend_app.backend_app.views.ServicioViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServicioStoredProcedureRepository servicioStoredProcedureRepository;

    @Autowired
    private ServicioViewRepository servicioViewRepository;

    @Autowired
    private ServicioFunctionRepository servicioFunctionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ServicioDTO> listadoServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        List<ServicioDTO> dtos = servicios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServicioDTO> buscarServicioPorId(long idServicio) {
        return servicioRepository.findById(idServicio).map(this::convertirADTO);
    }

    @Override
    public ServicioDTO convertirADTO(Servicio servicio) {
        ServicioDTO dto = new ServicioDTO();
        dto.setIdServicio(servicio.getIdServicio());
        dto.setNombreServicio(servicio.getNombreServicio());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setValorEstimado(servicio.getValorEstimado());
        dto.setStock(servicio.getStock());
        if (servicio.getCategory() != null){
            dto.setCategoriaId(servicio.getCategory().getIdCategoria());
            dto.setNombreCategoria(servicio.getCategory().getNombre());
        }
        if (servicio.getEstado() != null){
            dto.setEstadoId(servicio.getEstado().getIdEstado());
            dto.setNombreEstado(servicio.getEstado().getNombre());
        }
        dto.setImagenUrl(servicio.getImagenUrl());
        return dto;
    }

    @Override
    @Transactional
    public void insertarServicio(ServicioDTO servicioDTO) {
        servicioStoredProcedureRepository.insertarServicio(servicioDTO);
    }

    @Override
    @Transactional
    public void editarServicio(ServicioDTO servicioDTO) {
        servicioStoredProcedureRepository.editarBeneficio(servicioDTO);
    }

    @Override
    @Transactional
    public void eliminarServicio(ServicioDTO servicioDTO) {
        servicioStoredProcedureRepository.eliminarBeneficio(servicioDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServicioDTO> findAll(Pageable pageable) {
        return servicioRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioDTO> obtenerServiciosAsociados() {
        return servicioViewRepository.getServiciosAsociados();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioDTO> buscarServicioPorNombre(String nombreServicio) {
        return servicioFunctionRepository.buscarServicioPorNombre(nombreServicio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioDTO> listadoServiciosCompletos() {
        return servicioViewRepository.getServiciosCompletos();
    }


}
