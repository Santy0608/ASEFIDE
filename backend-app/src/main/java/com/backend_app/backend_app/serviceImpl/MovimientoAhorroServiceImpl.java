package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.MovimientosAhorroRepository;
import com.backend_app.backend_app.domain.MovimientosAhorro;
import com.backend_app.backend_app.dto.MovimientosAhorroDTO;
import com.backend_app.backend_app.repository.MovimientosAhorroStoredProcedureRepository;
import com.backend_app.backend_app.service.MovimientoAhorroService;
import com.backend_app.backend_app.views.MovimientosAhorroViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoAhorroServiceImpl implements MovimientoAhorroService{

    @Autowired
    private MovimientosAhorroRepository movimientosAhorroRepository;

    @Autowired
    private MovimientosAhorroStoredProcedureRepository movimientosAhorroStoredProcedureRepository;

    @Autowired
    private MovimientosAhorroViewRepository movimientosAhorroViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientosAhorroDTO> listadoMovimientosAhorro() {
        List<MovimientosAhorro> movimientosAhorros = movimientosAhorroRepository.findAll();
        List<MovimientosAhorroDTO> dtos = movimientosAhorros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovimientosAhorroDTO> buscarMovimientoAhorroPorId(long idMovimiento) {
        return movimientosAhorroRepository.findById(idMovimiento).map(this::convertirADTO);
    }

    @Override
    public MovimientosAhorroDTO convertirADTO(MovimientosAhorro movimientosAhorro) {
        MovimientosAhorroDTO dto = new MovimientosAhorroDTO();
        dto.setIdMovimiento(movimientosAhorro.getIdMovimiento());
        if (movimientosAhorro.getCuentasAhorro() != null){
            dto.setCuentasAhorroId(movimientosAhorro.getCuentasAhorro().getIdAhorro());
            dto.setMontoAporte(movimientosAhorro.getCuentasAhorro().getMontoAporte());
        }
        if (movimientosAhorro.getTransaccion() != null){
            dto.setTransaccionId(movimientosAhorro.getTransaccion().getIdTransaccion());
            dto.setMontoTota(movimientosAhorro.getTransaccion().getMontoTotal());
        }
        dto.setMonto(movimientosAhorro.getMonto());
        dto.setFechaDeposito(movimientosAhorro.getFechaDeposito());
        return dto;
    }

    @Override
    @Transactional
    public void nsertarMovimientoAhorro(MovimientosAhorroDTO movimientosAhorroDTO) {
        movimientosAhorroStoredProcedureRepository.registrarMovimientoAhorro(movimientosAhorroDTO);
    }

    @Override
    @Transactional
    public void actualizarMovimientoAhorro(MovimientosAhorroDTO movimientosAhorroDTO) {
        movimientosAhorroStoredProcedureRepository.editarMovimientoAhorro(movimientosAhorroDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientosAhorroDTO> findAll(Pageable pageable) {
        return movimientosAhorroRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientosAhorroDTO> listadoMovimientosAhorroCompleots() {
        return movimientosAhorroViewRepository.getMovimientosAhorrosCompletos();
    }

}
