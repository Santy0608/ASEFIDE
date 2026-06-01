package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.DatosAsociados;
import com.backend_app.backend_app.dto.AporteDTO;
import com.backend_app.backend_app.dto.DatosAsociadosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DatosAsociadosService {

    List<DatosAsociadosDTO> listadoDatosAsociados();

    Optional<DatosAsociadosDTO> buscarDatosAsociadosPorId(long idDatosAsociados);

    DatosAsociadosDTO convertirADTO(DatosAsociados datosAsociados);

    void inseertarDatosAsociados(DatosAsociadosDTO datosAsociadosDTO);

    void actualizarDatosAsociados(DatosAsociadosDTO datosAsociadosDTO);

    Page<DatosAsociadosDTO> findAll(Pageable pageable);

    List<DatosAsociadosDTO> listadoDatosAsociadosCompletos();

}
