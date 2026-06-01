package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.LugarEvento;
import com.backend_app.backend_app.dto.LugarEventoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LugarEventoService {

    List<LugarEventoDTO> listadoLugaresEventos();

    Optional<LugarEventoDTO> buscarLugarEventoPorId(long idLugarEvento);

    LugarEventoDTO convertirADTO(LugarEvento lugarEvento);

    void registrarLugarEvento(LugarEventoDTO lugarEventoDTO);

    void actualizarLugarEvento(LugarEventoDTO lugarEventoDTO);

    void eliminarLugarEvento(LugarEventoDTO lugarEventoDTO);

    Page<LugarEventoDTO> findAll(Pageable pageable);

    List<LugarEventoDTO> buscarLugarEvento(String nombreLugarEvento);

    List<LugarEventoDTO> listadoLugaresEventosCompletos();

}
