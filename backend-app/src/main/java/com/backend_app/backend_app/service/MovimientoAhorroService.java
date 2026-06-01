package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.MovimientosAhorro;
import com.backend_app.backend_app.dto.MovimientosAhorroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovimientoAhorroService {

    List<MovimientosAhorroDTO> listadoMovimientosAhorro();

    Optional<MovimientosAhorroDTO> buscarMovimientoAhorroPorId(long idMovimiento);

    MovimientosAhorroDTO convertirADTO(MovimientosAhorro movimientosAhorro);

    void nsertarMovimientoAhorro(MovimientosAhorroDTO movimientosAhorroDTO);

    void actualizarMovimientoAhorro(MovimientosAhorroDTO movimientosAhorroDTO);

    Page<MovimientosAhorroDTO> findAll(Pageable pageable);

    List<MovimientosAhorroDTO> listadoMovimientosAhorroCompleots();

}
