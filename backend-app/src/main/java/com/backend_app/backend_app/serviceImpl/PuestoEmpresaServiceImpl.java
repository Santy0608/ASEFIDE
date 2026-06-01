package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.PuestoEmpresaRepository;
import com.backend_app.backend_app.domain.PuestoEmpresa;
import com.backend_app.backend_app.dto.PuestoEmpresaDTO;
import com.backend_app.backend_app.functions.PuestoEmpresaFunctionRepository;
import com.backend_app.backend_app.repository.PuestoEmpresaStoredProcedureRepository;
import com.backend_app.backend_app.service.PuestoEmpresaService;
import com.backend_app.backend_app.views.PuestoEmpresaAsociadoViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PuestoEmpresaServiceImpl implements PuestoEmpresaService {

    @Autowired
    private PuestoEmpresaRepository puestoEmpresaRepository;

    @Autowired
    private PuestoEmpresaFunctionRepository puestoEmpresaFunctionRepository;

    @Autowired
    private PuestoEmpresaAsociadoViewRepository puestoEmpresaAsociadoViewRepository;

    @Autowired
    private PuestoEmpresaStoredProcedureRepository puestoEmpresaStoredProcedureRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PuestoEmpresaDTO> listadoPuestosEmpresas() {
        List<PuestoEmpresa> puestosEmpresas = puestoEmpresaRepository.findAll();
        List<PuestoEmpresaDTO> dtos = puestosEmpresas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PuestoEmpresaDTO> buscarPuestoEmpresaPorId(long idPuestoEmpresa) {
        return Optional.empty();
    }

    @Override
    public PuestoEmpresaDTO convertirADTO(PuestoEmpresa puestoEmpresa) {
        PuestoEmpresaDTO dto = new PuestoEmpresaDTO();
        dto.setIdPuestoEmpresa(puestoEmpresa.getIdPuestoEmpresa());
        dto.setPuestoEmpresa(puestoEmpresa.getPuestoEmpresa());
        if (puestoEmpresa.getEstado() != null){
            dto.setEstadoId(puestoEmpresa.getEstado().getIdEstado());
            dto.setNombreEstado(puestoEmpresa.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresaDTO) {
        puestoEmpresaStoredProcedureRepository.insertarPuestoEmpresa(puestoEmpresaDTO);
    }

    @Override
    @Transactional
    public void actualizarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresaDTO) {
        puestoEmpresaStoredProcedureRepository.editarPuestoEmpresa(puestoEmpresaDTO);
    }

    @Override
    @Transactional
    public void eliminarPuestoEmpresa(PuestoEmpresaDTO puestoEmpresaDTO) {
        puestoEmpresaStoredProcedureRepository.eliminarPuestoEmpresa(puestoEmpresaDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuestoEmpresaDTO> findAll(Pageable pageable) {
        return puestoEmpresaRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuestoEmpresaDTO> buscarPuestoEmpresa(String puestoEmpresa) {
        return puestoEmpresaFunctionRepository.buscarPuestoEmpresa(puestoEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuestoEmpresaDTO> listadoPuestosEmpresasCompletos() {
        return puestoEmpresaAsociadoViewRepository.getPuestosEmpresasCompletos();
    }
}
