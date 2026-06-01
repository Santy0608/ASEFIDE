package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.TelefonoRepository;
import com.backend_app.backend_app.domain.Telefono;
import com.backend_app.backend_app.dto.TelefonoDTO;
import com.backend_app.backend_app.functions.NumeroTelefonoFunctionRepository;
import com.backend_app.backend_app.repository.TelefonoStoredProcedureRepository;
import com.backend_app.backend_app.service.TelefonoService;
import com.backend_app.backend_app.views.NumeroTelefonoViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TelefonoServiceImpl implements TelefonoService {

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private TelefonoStoredProcedureRepository telefonoStoredProcedureRepository;

    @Autowired
    private NumeroTelefonoFunctionRepository numeroTelefonoFunctionRepository;

    @Autowired
    private NumeroTelefonoViewRepository numeroTelefonoViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TelefonoDTO> listadoTelefonos() {
        List<Telefono> telefonos = telefonoRepository.findAll();
        List<TelefonoDTO> dtos = telefonos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelefonoDTO> buscarTelefonoPorId(long idTelefono) {
        return telefonoRepository.findById(idTelefono).map(this::convertirADTO);
    }

    @Override
    public TelefonoDTO convertirADTO(Telefono telefono) {
        TelefonoDTO dto = new TelefonoDTO();
        dto.setIdTelefono(telefono.getIdTelefono());
        dto.setNumeroTelefono(telefono.getNumeroTelefono());
        if (telefono.getEstado() != null){
            dto.setEstadoId(telefono.getEstado().getIdEstado());
            dto.setNombreEstado(telefono.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    public void insertarTelefono(TelefonoDTO telefonoDTO) {
        telefonoStoredProcedureRepository.insertarTelefono(telefonoDTO);
    }

    @Override
    public void actualizarTelefono(TelefonoDTO telefonoDTO) {
        telefonoStoredProcedureRepository.editarTelefono(telefonoDTO);
    }

    @Override
    @Transactional
    public void eliminarTelefono(TelefonoDTO telefonoDTO) {
        telefonoStoredProcedureRepository.eliminarTelefono(telefonoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TelefonoDTO> findAll(Pageable pageable) {
        return telefonoRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TelefonoDTO> buscarNumeroTelefono(String numeroTelefono) {
        return numeroTelefonoFunctionRepository.buscarPorNumeroTelefono(numeroTelefono);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TelefonoDTO> listadoTelefonosCompletos() {
        return numeroTelefonoViewRepository.getTelefonosCompletos();
    }
}
