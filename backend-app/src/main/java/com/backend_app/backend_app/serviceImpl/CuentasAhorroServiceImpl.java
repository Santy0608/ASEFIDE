package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.CuentasAhorroRepository;
import com.backend_app.backend_app.domain.CuentasAhorro;
import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import com.backend_app.backend_app.functions.AhorroFunctionRepository;
import com.backend_app.backend_app.repository.CuentasAhorroStoredProcedureRepository;
import com.backend_app.backend_app.service.CuentasAhorroService;
import com.backend_app.backend_app.views.CuentasAhorroViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuentasAhorroServiceImpl implements CuentasAhorroService {

    @Autowired
    private CuentasAhorroRepository cuentasAhorroRepository;

    @Autowired
    private AhorroFunctionRepository ahorroFunctionRepository;

    @Autowired
    private CuentasAhorroStoredProcedureRepository cuentasAhorroStoredProcedureRepository;

    @Autowired
    private CuentasAhorroViewRepository cuentasAhorroViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CuentasAhorroDTO> listadoCuentasAhorro() {
        List<CuentasAhorro> cuentasAhorros = cuentasAhorroRepository.findAll();
        List<CuentasAhorroDTO> dtos = cuentasAhorros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentasAhorroDTO> buscarCuentasAhorroPorId(long idCuentasAhorro) {
        return cuentasAhorroRepository.findById(idCuentasAhorro).map(this::convertirADTO);
    }

    @Override
    public CuentasAhorroDTO convertirADTO(CuentasAhorro cuentasAhorro) {
        CuentasAhorroDTO dto = new CuentasAhorroDTO();
        dto.setIdAhorro(cuentasAhorro.getIdAhorro());
        if (cuentasAhorro.getUsuario() != null){
            dto.setUsuarioId(cuentasAhorro.getUsuario().getIdUsuario());
            dto.setIdentificacion(cuentasAhorro.getUsuario().getIdentificacion());
            dto.setNombreUsuario(cuentasAhorro.getUsuario().getNombreUsuario());
            dto.setApellidoPaterno(cuentasAhorro.getUsuario().getApellidoPaterno());
        }
        dto.setMontoAporte(cuentasAhorro.getMontoAporte());
        dto.setFechaApertura(cuentasAhorro.getFechaApertura());
        if (cuentasAhorro.getTipoAhorro() != null){
            dto.setTipoAhorroId(cuentasAhorro.getTipoAhorro().getIdTipoAhorro());
            dto.setNombreTipoAhorro(cuentasAhorro.getTipoAhorro().getNombre());
        }
        dto.setSaldoActual(cuentasAhorro.getSaldoActual());
        if (cuentasAhorro.getEstado() != null){
            dto.setEstadoId(cuentasAhorro.getEstado().getIdEstado());
            dto.setNombreEstado(cuentasAhorro.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarCuentaAhorro(CuentasAhorroDTO cuentasAhorroDTO) {
        cuentasAhorroStoredProcedureRepository.insertarCuentaAhorro(cuentasAhorroDTO);
    }

    @Override
    @Transactional
    public void actualizarCuentasAhorro(CuentasAhorroDTO cuentasAhorroDTO) {
        cuentasAhorroStoredProcedureRepository.editarCuentasAhorro(cuentasAhorroDTO);
    }

    @Override
    @Transactional
    public void eliminarCuentasAhorro(CuentasAhorroDTO cuentasAhorroDTO) {
        cuentasAhorroStoredProcedureRepository.eliminarCuentasAhorro(cuentasAhorroDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CuentasAhorroDTO> findAll(Pageable pageable) {
        return cuentasAhorroRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Double sumaTotalAhorros() {
        return ahorroFunctionRepository.sumaTotalAhorros();
    }

    @Override
    @Transactional(readOnly = true)
    public Double promedioAhorros() {
        return ahorroFunctionRepository.promedioAhorros();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentasAhorroDTO> reporteAhorrosUsuario(Long idUsuario) {
        return ahorroFunctionRepository.reporteAhorrosUsuario(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentasAhorroDTO> usuariosMayorAhorro(BigDecimal monto) {
        return ahorroFunctionRepository.usuariosMayorAhorro(monto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentasAhorroDTO> top10MasAhorros() {
        return ahorroFunctionRepository.top10MasAhorros();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentasAhorroDTO> top10MenosAhorros() {
        return ahorroFunctionRepository.top10MenosAhorros();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentasAhorroDTO> listadoCuentasAhorrosCompletos() {
        return cuentasAhorroViewRepository.getCuentasAhorrosCompletos();
    }


}
