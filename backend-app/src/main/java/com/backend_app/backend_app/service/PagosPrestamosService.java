package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.PagosPrestamos;
import com.backend_app.backend_app.dto.PagosPrestamosDTO;
import com.backend_app.backend_app.dto.PrestamoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PagosPrestamosService {

    List<PagosPrestamosDTO> listadoPagosPrestamos();

    Optional<PagosPrestamosDTO> buscarPagoPrestamoPorId(long idPagoPrestamo);

    PagosPrestamosDTO convertirADTO(PagosPrestamos pagosPrestamos);

    void registrarPagoPrestamo(PagosPrestamosDTO pagosPrestamosDTO);

    void actualizarPagoPrestamo(PagosPrestamosDTO pagosPrestamosDTO);

    Page<PagosPrestamosDTO> findAll(Pageable pageable);

    List<PagosPrestamosDTO> listadoPagosPrestamosCompletos();

}
