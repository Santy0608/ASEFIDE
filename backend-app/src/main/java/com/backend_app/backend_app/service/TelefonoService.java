package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Telefono;
import com.backend_app.backend_app.dto.TelefonoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TelefonoService {

    List<TelefonoDTO> listadoTelefonos();

    Optional<TelefonoDTO> buscarTelefonoPorId(long idTelefono);

    TelefonoDTO convertirADTO(Telefono telefono);

    void insertarTelefono(TelefonoDTO telefonoDTO);

    void actualizarTelefono(TelefonoDTO telefonoDTO);

    void eliminarTelefono(TelefonoDTO telefonoDTO);

    Page<TelefonoDTO> findAll(Pageable pageable);

    List<TelefonoDTO> buscarNumeroTelefono(String numeroTelefono);

    List<TelefonoDTO> listadoTelefonosCompletos();

}
