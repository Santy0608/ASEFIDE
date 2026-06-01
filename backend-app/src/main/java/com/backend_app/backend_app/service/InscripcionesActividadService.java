package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.InscripcionesActividad;
import com.backend_app.backend_app.dto.InscripcionesActividadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InscripcionesActividadService {

    List<InscripcionesActividadDTO> listadoInscripcionesActividad();

    Optional<InscripcionesActividadDTO> buscarInscripcionActividadPorId(long idInscripcion);

    InscripcionesActividadDTO convertirADTO(InscripcionesActividad inscripcionesActividad);

    void agregarInscripcionActividad(InscripcionesActividadDTO inscripcionesActividadDTO);

    void actualizarInscripcionActividad(InscripcionesActividadDTO inscripcionesActividadDTO);

    void eliminarInscripcionActividad(InscripcionesActividadDTO inscripcionesActividadDTO);

    Page<InscripcionesActividadDTO> findAll(Pageable pageable);

    List<InscripcionesActividadDTO> listadoInscripcionesActividadesCompletas();


}
