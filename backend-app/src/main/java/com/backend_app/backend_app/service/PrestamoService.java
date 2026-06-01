package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Estado;
import com.backend_app.backend_app.domain.Prestamo;
import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.dto.PrestamoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PrestamoService {

    List<PrestamoDTO> listadoPrestamos();

    Optional<PrestamoDTO> buscarPrestamoPorId(long idPrestamo);

    PrestamoDTO convertirADTO(Prestamo prestamo);

    void insertarPrestamo(PrestamoDTO prestamoDTO);

    void actualizarPrestamo(PrestamoDTO prestamoDTO);

    void eliminarPrestamo(PrestamoDTO prestamoDTO);

    Page<PrestamoDTO> findAll(Pageable pageable);

    List<EstadoDTO> obtenerEstadosPrestamos();

    List<PrestamoDTO> listadoPrestamosCompletos();

    List<EstadoDTO> prestamosPorEstado();


}
