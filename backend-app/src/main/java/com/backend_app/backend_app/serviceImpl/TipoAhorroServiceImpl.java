package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.TipoAhorroRepository;
import com.backend_app.backend_app.domain.TipoAhorro;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.functions.TipoAhorroFunctionRepository;
import com.backend_app.backend_app.repository.TipoAhorroStoredProcedureRepository;
import com.backend_app.backend_app.service.TipoAhorroService;
import com.backend_app.backend_app.views.TipoAhorroViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoAhorroServiceImpl implements TipoAhorroService {

    @Autowired
    private TipoAhorroRepository tipoAhorroRepository;

    @Autowired
    private TipoAhorroFunctionRepository tipoAhorroFunctionRepository;

    @Autowired
    private TipoAhorroStoredProcedureRepository tipoAhorroStoredProcedureRepository;

    @Autowired
    private TipoAhorroViewRepository tipoAhorroViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoAhorroDTO> listadoTiposAhorro() {
       List<TipoAhorro> tipoAhorros = tipoAhorroRepository.findAll();
       List<TipoAhorroDTO> dtos = tipoAhorros.stream()
               .map(this::convertirADTO)
               .collect(Collectors.toList());
       return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoAhorroDTO> buscarTipoAhorroPorId(long idTipoAhorro) {
        return tipoAhorroRepository.findById(idTipoAhorro).map(this::convertirADTO);
    }

    @Override
    public TipoAhorroDTO convertirADTO(TipoAhorro tipoAhorro) {
        TipoAhorroDTO dto = new TipoAhorroDTO();
        dto.setIdTipoAhorro(tipoAhorro.getIdTipoAhorro());
        dto.setNombre(tipoAhorro.getNombre());
        dto.setDescripcion(tipoAhorro.getDescripcion());
        if (tipoAhorro.getEstado() != null){
            dto.setEstadoId(tipoAhorro.getEstado().getIdEstado());
            dto.setNombreEstado(tipoAhorro.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarTipoAhorro(TipoAhorroDTO tipoAhorro) {
        tipoAhorroStoredProcedureRepository.insertarTipoAhorro(tipoAhorro);
    }

    @Override
    @Transactional
    public void actualizarTipoAhorro(TipoAhorroDTO tipoAhorro) {
        tipoAhorroStoredProcedureRepository.editarTipoAhorro(tipoAhorro);
    }

    @Override
    @Transactional
    public void eliminarTipoAhorro(TipoAhorroDTO tipoAhorro) {
        tipoAhorroStoredProcedureRepository.eliminarTipoAhorro(tipoAhorro);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoAhorroDTO> findAll(Pageable pageable) {
        return tipoAhorroRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoAhorroDTO> buscarTipoAhorroPorNombre(String nombreTipoAhorro) {
        return tipoAhorroFunctionRepository.buscarActividadPorTipoAhorro(nombreTipoAhorro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoAhorroDTO> listadoTiposAhorrosCompletos() {
        return tipoAhorroViewRepository.getTiposAhorrosCompletos();
    }
}
