package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.TipoAhorro;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TipoAhorroService {

    List<TipoAhorroDTO> listadoTiposAhorro();

    Optional<TipoAhorroDTO> buscarTipoAhorroPorId(long idTipoAhorro);

    TipoAhorroDTO convertirADTO(TipoAhorro tipoAhorro);

    void insertarTipoAhorro(TipoAhorroDTO tipoAhorro);

    void actualizarTipoAhorro(TipoAhorroDTO tipoAhorro);

    void eliminarTipoAhorro(TipoAhorroDTO tipoAhorro);

    Page<TipoAhorroDTO> findAll(Pageable pageable);

    List<TipoAhorroDTO> buscarTipoAhorroPorNombre(String nombreTipoAhorro);

    List<TipoAhorroDTO> listadoTiposAhorrosCompletos();

}
