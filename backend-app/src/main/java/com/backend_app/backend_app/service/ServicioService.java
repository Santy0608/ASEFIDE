package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Servicio;
import com.backend_app.backend_app.dto.ServicioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ServicioService {

    List<ServicioDTO> listadoServicios();

    Optional<ServicioDTO> buscarServicioPorId(long idServicio);

    ServicioDTO convertirADTO(Servicio servicio);

    void insertarServicio(ServicioDTO servicioDTO);

    void editarServicio(ServicioDTO servicioDTO);

    void eliminarServicio(ServicioDTO servicioDTO);

    Page<ServicioDTO> findAll(Pageable pageable);

    List<ServicioDTO> obtenerServiciosAsociados();

    List<ServicioDTO> buscarServicioPorNombre(String nombreServicio);

    List<ServicioDTO> listadoServiciosCompletos();

}
