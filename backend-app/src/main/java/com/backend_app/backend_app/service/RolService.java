package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Rol;
import com.backend_app.backend_app.dto.RolDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RolService {

    List<RolDTO> listadoRoles();

    Optional<RolDTO> buscarRolPorId(long idRol);

    RolDTO convertirADTO(Rol rol);

    void insertarRol(RolDTO rolDTO);

    void actualizarRol(RolDTO rolDTO);

    void eliminarRol(RolDTO rolDTO);

    List<RolDTO> listadoRolesCompletos();



}
