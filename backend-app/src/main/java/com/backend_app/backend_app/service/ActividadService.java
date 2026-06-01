package com.backend_app.backend_app.service;


import com.backend_app.backend_app.domain.Actividad;
import com.backend_app.backend_app.dto.ActividadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ActividadService {

    List<ActividadDTO> listadoActivides();

    Optional<ActividadDTO> buscarActividadPorId(long idActividad);

    public ActividadDTO convertirADTO(Actividad actividad);

    void insertarActividad(ActividadDTO actividadDTO);

    void actualizarActividad(ActividadDTO actividadDTO);

    void elimminarActividad(ActividadDTO actividadDTO);

    Page<ActividadDTO> findAll(Pageable pageable);

    List<ActividadDTO> obtenerActividadesAsociados();

    List<ActividadDTO> buscarActividadPorNombre(String nombre);

    List<ActividadDTO> listadoActividadesProgramadas();

    List<ActividadDTO> listadoActividadesCompletas();

}
