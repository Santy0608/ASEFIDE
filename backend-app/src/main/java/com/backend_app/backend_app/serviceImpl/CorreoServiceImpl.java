package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.CorreoRepository;
import com.backend_app.backend_app.domain.Correo;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.functions.CorreoElectronicoFunctionRepository;
import com.backend_app.backend_app.repository.CorreoStoredProcedureRepository;
import com.backend_app.backend_app.service.CorreoService;
import com.backend_app.backend_app.views.CorreoViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CorreoServiceImpl implements CorreoService {

    @Autowired
    private CorreoRepository correoRepository;

    @Autowired
    private CorreoStoredProcedureRepository correoStoredProcedureRepository;

    @Autowired
    private CorreoElectronicoFunctionRepository correoElectronicoFunctionRepository;

    @Autowired
    private CorreoViewRepository correoViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CorreoDTO> listadoCorreos() {
        List<Correo> correos = correoRepository.findAll();
        List<CorreoDTO> dtos = correos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CorreoDTO> buscarCorreoPorId(long idCorreo) {
        return correoRepository.findById(idCorreo).map(this::convertirADTO);
    }

    @Override
    public CorreoDTO convertirADTO(Correo correo) {
        CorreoDTO dto = new CorreoDTO();
        dto.setIdCorreo(correo.getIdCorreo());
        dto.setCorreoElectronico(correo.getCorreoElectronico());
        if (correo.getEstado() != null){
            dto.setEstadoId(correo.getEstado().getIdEstado());
            dto.setNombreEstado(correo.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarCorreo(CorreoDTO correoDTO) {
        correoStoredProcedureRepository.insertarCorreo(correoDTO);
    }

    @Override
    @Transactional
    public void actualizarCorreo(CorreoDTO correoDTO) {
        correoStoredProcedureRepository.editarCorreo(correoDTO);
    }

    @Override
    @Transactional
    public void eliminarCorreo(CorreoDTO correoDTO) {
        correoStoredProcedureRepository.eliminarCorreo(correoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CorreoDTO> findAll(Pageable pageable) {
        return correoRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CorreoDTO> buscarCorreoElectronico(String correoElectronico) {
        return correoElectronicoFunctionRepository.buscarPorCorreoElectronico(correoElectronico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CorreoDTO> listadoCorreosCompletos() {
        return correoViewRepository.getCorreosCompletos();
    }


}
