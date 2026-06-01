package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Direccion;
import com.backend_app.backend_app.dto.DireccionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DireccionService {

    List<DireccionDTO> listadoDirecciones();

    Optional<DireccionDTO> buscarDireccionPorId(long idDireccion);

    DireccionDTO convertirADTO(Direccion direccion);

    void insertarDireccion(DireccionDTO direccionDTO);

    void actualizarDireccion(DireccionDTO direccionDTO);

    void eliminarDireccion(DireccionDTO direccionDTO);

    Page<DireccionDTO> findAll(Pageable pageable);

    List<DireccionDTO> buscarDireccionPorProvincia(String provincia);

    List<DireccionDTO> listadoDireccionesCompletas();

}
