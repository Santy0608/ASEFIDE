package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.RolRepository;
import com.backend_app.backend_app.domain.Rol;
import com.backend_app.backend_app.dto.RolDTO;
import com.backend_app.backend_app.repository.RolStoredProcedureRepository;
import com.backend_app.backend_app.service.RolService;
import com.backend_app.backend_app.views.RolViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolStoredProcedureRepository rolStoredProcedureRepository;

    @Autowired
    private RolViewRepository rolViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> listadoRoles() {
        List<Rol> roles = rolRepository.findAll();
        List<RolDTO> dtos = roles.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RolDTO> buscarRolPorId(long idRol) {
        return rolRepository.findById(idRol).map(this::convertirADTO);
    }

    @Override
    public RolDTO convertirADTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombreRol(rol.getNombreRol());
        if (rol.getEstado() != null){
            dto.setEstadoId(rol.getEstado().getIdEstado());
            dto.setNombreEstado(rol.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarRol(RolDTO rolDTO) {
        rolStoredProcedureRepository.insertarRol(rolDTO);
    }

    @Override
    @Transactional
    public void actualizarRol(RolDTO rolDTO) {
        rolStoredProcedureRepository.editarRol(rolDTO);
    }

    @Override
    @Transactional
    public void eliminarRol(RolDTO rolDTO) {
        rolStoredProcedureRepository.eliminarRol(rolDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> listadoRolesCompletos() {
        return rolViewRepository.getRolesCompletos();
    }



}
