package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Correo;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.TelefonoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface CorreoService {

    List<CorreoDTO> listadoCorreos();

    Optional<CorreoDTO> buscarCorreoPorId(long idCorreo);

    CorreoDTO convertirADTO(Correo correo);

    void insertarCorreo(CorreoDTO correoDTO);

    void actualizarCorreo(CorreoDTO correoDTO);

    void eliminarCorreo(CorreoDTO correoDTO);

    Page<CorreoDTO> findAll(Pageable pageable);

    List<CorreoDTO> buscarCorreoElectronico(String correoElectronico);

    List<CorreoDTO> listadoCorreosCompletos();

}
