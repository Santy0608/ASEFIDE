package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Estado;
import com.backend_app.backend_app.dto.EstadoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EstadoService {

    List<EstadoDTO> listadoEstados();

    Optional<EstadoDTO> buscarEstadoPorId(long idEstado);

    EstadoDTO convertirADTO(Estado estado);

    void insertarEstado(EstadoDTO estadoDTO);

    void actualizarEstado(EstadoDTO estadoDTO);

    Page<EstadoDTO> findAll(Pageable pageable);

    List<EstadoDTO> listadoEstadosCompletos();


}
