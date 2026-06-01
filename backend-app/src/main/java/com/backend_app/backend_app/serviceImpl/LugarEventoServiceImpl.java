package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.LugarEventoRepository;
import com.backend_app.backend_app.domain.LugarEvento;
import com.backend_app.backend_app.dto.LugarEventoDTO;
import com.backend_app.backend_app.functions.LugarEventoFunctionRepository;
import com.backend_app.backend_app.repository.LugarEventoStoredProcedureRepository;
import com.backend_app.backend_app.service.LugarEventoService;
import com.backend_app.backend_app.views.LugarEventoViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LugarEventoServiceImpl implements LugarEventoService {

    @Autowired
    private LugarEventoRepository lugarEventoRepository;

    @Autowired
    private LugarEventoStoredProcedureRepository lugarEventoStoredProcedureRepository;

    @Autowired
    private LugarEventoFunctionRepository lugarEventoFunctionRepository;

    @Autowired
    private LugarEventoViewRepository lugarEventoViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LugarEventoDTO> listadoLugaresEventos() {
        List<LugarEvento> lugarEventos = lugarEventoRepository.findAll();
        List<LugarEventoDTO> dtos = lugarEventos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LugarEventoDTO> buscarLugarEventoPorId(long idLugarEvento) {
        return lugarEventoRepository.findById(idLugarEvento).map(this::convertirADTO);
    }

    @Override
    public LugarEventoDTO convertirADTO(LugarEvento lugarEvento) {
        LugarEventoDTO dto = new LugarEventoDTO();
        dto.setIdLugarEvento(lugarEvento.getIdLugarEvento());
        dto.setNombreLugar(lugarEvento.getNombreLugar());
        if (lugarEvento.getIdLugarEvento() != null){
            dto.setEstadoId(lugarEvento.getEstado().getIdEstado());
            dto.setNombreEstado(lugarEvento.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void registrarLugarEvento(LugarEventoDTO lugarEventoDTO) {
        lugarEventoStoredProcedureRepository.insertarLugarEvento(lugarEventoDTO);
    }

    @Override
    @Transactional
    public void actualizarLugarEvento(LugarEventoDTO lugarEventoDTO) {
        lugarEventoStoredProcedureRepository.editarLugarEvento(lugarEventoDTO);
    }

    @Override
    @Transactional
    public void eliminarLugarEvento(LugarEventoDTO lugarEventoDTO) {
        lugarEventoStoredProcedureRepository.eliminarLugarEvento(lugarEventoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LugarEventoDTO> findAll(Pageable pageable) {
        return lugarEventoRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarEventoDTO> buscarLugarEvento(String nombreLugarEvento) {
        return lugarEventoFunctionRepository.buscarLugarEvento(nombreLugarEvento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarEventoDTO> listadoLugaresEventosCompletos() {
        return lugarEventoViewRepository.getLugaresEventosCompletos();
    }
}
