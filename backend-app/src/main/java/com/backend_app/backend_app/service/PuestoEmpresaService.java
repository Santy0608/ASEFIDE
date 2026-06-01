package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.PuestoEmpresa;
import com.backend_app.backend_app.dto.PuestoEmpresaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PuestoEmpresaService {

    List<PuestoEmpresaDTO> listadoPuestosEmpresas();

    Optional<PuestoEmpresaDTO> buscarPuestoEmpresaPorId(long idPuestoEmpresa);

    PuestoEmpresaDTO convertirADTO(PuestoEmpresa puestoEmpresa);

    void insertarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresaDTO);

    void actualizarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresaDTO);

    void eliminarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresaDTO);

    Page<PuestoEmpresaDTO> findAll(Pageable pageable);

    List<PuestoEmpresaDTO> buscarPuestoEmpresa(String puestoEmpresa);

    List<PuestoEmpresaDTO> listadoPuestosEmpresasCompletos();

}
