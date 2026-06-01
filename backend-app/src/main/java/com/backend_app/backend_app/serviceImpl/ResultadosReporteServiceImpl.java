package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.ResultadosReporteRepository;
import com.backend_app.backend_app.domain.ResultadosReporte;
import com.backend_app.backend_app.dto.ResultadosReporteDTO;
import com.backend_app.backend_app.repository.ResultadosReporteStoredProcedureRepository;
import com.backend_app.backend_app.service.ResultadosReporteService;
import com.backend_app.backend_app.views.ResultadosReportesViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultadosReporteServiceImpl implements ResultadosReporteService {

    @Autowired
    private ResultadosReporteRepository resultadosReporteRepository;

    @Autowired
    private ResultadosReporteStoredProcedureRepository resultadosReporteStoredProcedureRepositorys;

    @Autowired
    private ResultadosReportesViewRepository resultadosReportesViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ResultadosReporteDTO> listadoResultadosReporte() {
        List<ResultadosReporte> resultadosReportes = resultadosReporteRepository.findAll();
        List<ResultadosReporteDTO> dtos = resultadosReportes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResultadosReporteDTO> buscarResultadosReportePorId(long idResultado) {
        return resultadosReporteRepository.findById(idResultado).map(this::convertirADTO);
    }

    @Override
    public ResultadosReporteDTO convertirADTO(ResultadosReporte resultadosReporte) {
        ResultadosReporteDTO dto = new ResultadosReporteDTO();
        dto.setIdResultado(resultadosReporte.getIdResultado());
        if (resultadosReporte.getReporte() != null){
            dto.setReporteId(resultadosReporte.getReporte().getIdReporte());
            dto.setTotalRegistros(resultadosReporte.getReporte().getTotalRegistros());
        }
        dto.setMetricaNombre(resultadosReporte.getMetricaNombre());
        dto.setMetricaValor(resultadosReporte.getMetricaValor());
        return dto;
    }

    @Override
    @Transactional
    public void insertarResultado(ResultadosReporteDTO resultadosReporteDTO) {
        resultadosReporteStoredProcedureRepositorys.registrarResultadoReporte(resultadosReporteDTO);
    }

    @Override
    @Transactional
    public void actualizarResultado(ResultadosReporteDTO resultadosReporteDTO) {
        resultadosReporteStoredProcedureRepositorys.registrarResultadoReporte(resultadosReporteDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResultadosReporteDTO> findAll(Pageable pageable) {
        return resultadosReporteRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultadosReporteDTO> listadoResultadosReportesCompletos() {
        return resultadosReportesViewRepository.getResultadosReportesCompletos();
    }


}
