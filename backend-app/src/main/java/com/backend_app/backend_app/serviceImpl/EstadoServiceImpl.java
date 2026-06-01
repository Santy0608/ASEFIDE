package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.EstadoRepository;
import com.backend_app.backend_app.domain.Estado;
import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.repository.EstadoStoredProcedureRepository;
import com.backend_app.backend_app.service.EstadoService;
import com.backend_app.backend_app.views.EstadoViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadoServiceImpl implements EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoStoredProcedureRepository estadoStoredProcedureRepository;

    @Autowired
    private EstadoViewRepository estadoViewRepository;

    @Override
    public List<EstadoDTO> listadoEstados() {
        List<Estado> estados = estadoRepository.findAll();
        List<EstadoDTO> dtos = estados.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public Optional<EstadoDTO> buscarEstadoPorId(long idEstado) {
        return estadoRepository.findById(idEstado).map(this::convertirADTO);
    }

    @Override
    public EstadoDTO convertirADTO(Estado estado) {
        EstadoDTO dto = new EstadoDTO();
        dto.setIdEstado(estado.getIdEstado());
        dto.setNombre(estado.getNombre());
        return dto;
    }

    @Override
    @Transactional
    public void insertarEstado(EstadoDTO estadoDTO) {
        estadoStoredProcedureRepository.insertarEstado(estadoDTO);
    }

    @Override
    @Transactional
    public void actualizarEstado(EstadoDTO estadoDTO) {
        estadoStoredProcedureRepository.editarEstado(estadoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoDTO> findAll(Pageable pageable) {
        return estadoRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDTO> listadoEstadosCompletos() {
        return estadoViewRepository.getEstadosCompletos();
    }


}
