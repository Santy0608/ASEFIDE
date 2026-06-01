package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Beneficio;
import com.backend_app.backend_app.dto.BeneficioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BeneficioService {

    List<BeneficioDTO> listadoBeneficios();

    Optional<BeneficioDTO> buscarBeneficioPorId(long idBeneficio);

    public BeneficioDTO convertirADTO(Beneficio beneficio);

    void insertarBeneficio(BeneficioDTO beneficioDTO);

    void actualizarBeneficio(BeneficioDTO beneficioDTO);

    void eliminarBeneficio(BeneficioDTO beneficioDTO);

    Page<BeneficioDTO> findAll(Pageable pageable);

    List<BeneficioDTO> obtenerBeneficiosAsociados();

    List<BeneficioDTO> buscarBeneficioPorNombre(String nombreBeneficio);

    List<BeneficioDTO> listadoBeneficiosCompletos();

}
