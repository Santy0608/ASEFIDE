package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.DatosAsociadosRepository;
import com.backend_app.backend_app.domain.DatosAsociados;
import com.backend_app.backend_app.dto.AporteDTO;
import com.backend_app.backend_app.dto.AporteUsuarioDTO;
import com.backend_app.backend_app.dto.DatosAsociadosDTO;
import com.backend_app.backend_app.repository.DatosAsociadosStoredProcedureRepository;
import com.backend_app.backend_app.service.DatosAsociadosService;
import com.backend_app.backend_app.views.DatosAsociadosViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatosAsociadosServiceImpl implements DatosAsociadosService {

    @Autowired
    private DatosAsociadosRepository datosAsociadosRepository;

    @Autowired
    private DatosAsociadosStoredProcedureRepository datosAsociadosStoredProcedureRepository;

    @Autowired
    private DatosAsociadosViewRepository datosAsociadosViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DatosAsociadosDTO> listadoDatosAsociados() {
        List<DatosAsociados> datosAsociados = datosAsociadosRepository.findAll();
        List<DatosAsociadosDTO> dtos = datosAsociados.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DatosAsociadosDTO> buscarDatosAsociadosPorId(long idDatosAsociados) {
        return datosAsociadosRepository.findById(idDatosAsociados).map(this::convertirADTO);
    }

    @Override
    public DatosAsociadosDTO convertirADTO(DatosAsociados datosAsociados) {
        DatosAsociadosDTO dto = new DatosAsociadosDTO();
        dto.setIdDatosAsociados(datosAsociados.getIdDatosAsociados());
        dto.setFechaAfiliacion(datosAsociados.getFechaAfiliacion());
        if (datosAsociados.getPuestoEmpresa() != null) {
            dto.setPuestoEmpresaId(datosAsociados.getPuestoEmpresa().getIdPuestoEmpresa());
            dto.setNombrePuestoEmpresa(datosAsociados.getPuestoEmpresa().getPuestoEmpresa());
        }

        if (datosAsociados.getAportes() != null) {
            List<AporteUsuarioDTO> aportesDTO = datosAsociados.getAportes().stream()
                    .map(aporte -> {
                        AporteUsuarioDTO aporteDTO = new AporteUsuarioDTO();
                        aporteDTO.setIdAporte(aporte.getIdAporte());
                        aporteDTO.setMonto(aporte.getMonto());
                        aporteDTO.setFechaInicio(aporte.getFechaInicio());
                        aporteDTO.setFechaFinal(aporte.getFechaFinal());
                        return aporteDTO;
                    })
                    .collect(Collectors.toList());
            dto.setAportes(aportesDTO);
        }
        return dto;
    }

    @Override
    @Transactional
    public void inseertarDatosAsociados(DatosAsociadosDTO datosAsociadosDTO) {
        datosAsociadosStoredProcedureRepository.insertarDatosAsociados(datosAsociadosDTO);
    }

    @Override
    @Transactional
    public void actualizarDatosAsociados(DatosAsociadosDTO datosAsociadosDTO) {
        datosAsociadosStoredProcedureRepository.editarDatosAsociados(datosAsociadosDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DatosAsociadosDTO> findAll(Pageable pageable) {
        return datosAsociadosRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatosAsociadosDTO> listadoDatosAsociadosCompletos() {
        return datosAsociadosViewRepository.getDatosAsociadosCompletos();
    }


}
