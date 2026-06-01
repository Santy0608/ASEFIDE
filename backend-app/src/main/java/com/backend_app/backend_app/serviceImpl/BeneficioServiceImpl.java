package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.BeneficioRepository;
import com.backend_app.backend_app.domain.Beneficio;
import com.backend_app.backend_app.dto.BeneficioDTO;
import com.backend_app.backend_app.functions.BeneficioFunctionRepository;
import com.backend_app.backend_app.repository.BeneficioStoredProcedureRepository;
import com.backend_app.backend_app.service.BeneficioService;
import com.backend_app.backend_app.views.BeneficioViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeneficioServiceImpl implements BeneficioService {

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private BeneficioViewRepository beneficioViewRepository;

    @Autowired
    private BeneficioStoredProcedureRepository beneficioStoredProcedureRepository;

    @Autowired
    private BeneficioFunctionRepository beneficioFunctionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BeneficioDTO> listadoBeneficios() {
        List<Beneficio> beneficios = beneficioRepository.findAll();
        List<BeneficioDTO> dto = beneficios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeneficioDTO> buscarBeneficioPorId(long idBeneficio) {
        return beneficioRepository.findById(idBeneficio).map(this::convertirADTO);
    }

    @Override
    public BeneficioDTO convertirADTO(Beneficio beneficio) {
        BeneficioDTO dto = new BeneficioDTO();
        dto.setIdBeneficio(beneficio.getIdBeneficio());
        dto.setNombreBeneficio(beneficio.getNombreBeneficio());
        dto.setDescripcion(beneficio.getDescripcion());
        if (beneficio.getCategoria() != null) {
            dto.setCategoriaId(beneficio.getCategoria().getIdCategoria());
            dto.setNombreCategoria(beneficio.getCategoria().getNombre());
        }
        if (beneficio.getEstado() != null){
            dto.setEstadoId(beneficio.getEstado().getIdEstado());
            dto.setNombreEstado(beneficio.getEstado().getNombre());
        }
        dto.setImagenUrl(beneficio.getImagenUrl());
        return dto;
    }

    @Override
    @Transactional
    public void insertarBeneficio(BeneficioDTO beneficioDTO) {
        beneficioStoredProcedureRepository.insertarBeneficio(beneficioDTO);
    }

    @Override
    @Transactional
    public void actualizarBeneficio(BeneficioDTO beneficioDTO) {
        beneficioStoredProcedureRepository.editarBeneficio(beneficioDTO);
    }

    @Override
    @Transactional
    public void eliminarBeneficio(BeneficioDTO beneficioDTO) {
        beneficioStoredProcedureRepository.eliminarBeneficio(beneficioDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BeneficioDTO> findAll(Pageable pageable) {
        return beneficioRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficioDTO> obtenerBeneficiosAsociados() {
        return beneficioViewRepository.getBeneficiosAsociados();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficioDTO> buscarBeneficioPorNombre(String nombreBeneficio) {
        return beneficioFunctionRepository.buscarBeneficioPorNombre(nombreBeneficio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficioDTO> listadoBeneficiosCompletos() {
        return beneficioViewRepository.getBeneficiosCompletos();
    }


}
