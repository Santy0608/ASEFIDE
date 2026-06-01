package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.TransaccionRepository;
import com.backend_app.backend_app.domain.Transaccion;
import com.backend_app.backend_app.dto.TransaccionDTO;
import com.backend_app.backend_app.functions.TransaccionFunctionRepository;
import com.backend_app.backend_app.repository.TransaccionStoredProcedureRepository;
import com.backend_app.backend_app.service.TransaccionService;
import com.backend_app.backend_app.views.TransaccionViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private TransaccionFunctionRepository transaccionFunctionRepository;

    @Autowired
    private TransaccionStoredProcedureRepository transaccionStoredProcedureRepository;

    @Autowired
    private TransaccionViewRepository transaccionViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionDTO> listadoTransacciones() {
        List<Transaccion> transacciones = transaccionRepository.findAll();
        List<TransaccionDTO> dtos = transacciones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransaccionDTO> buscarTransaccionPorId(long idTransaccioin) {
        return transaccionRepository.findById(idTransaccioin).map(this::convertirADTO);
    }

    @Override
    public TransaccionDTO convertirADTO(Transaccion transaccion) {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setIdTransaccion(transaccion.getIdTransaccion());
        dto.setFechaTransaccion(transaccion.getFechaTransaccion());
        if (transaccion.getTipoTransaccion() != null) {
            dto.setTipoTransaccionId(transaccion.getTipoTransaccion().getIdTipoTransaccion());
            dto.setNombreTipoTransaccion(transaccion.getTipoTransaccion().getNombre());
        }
        dto.setMontoTotal(transaccion.getMontoTotal());
        if (transaccion.getUsuario() != null){
            dto.setUsuarioId(transaccion.getUsuario().getIdUsuario());
            dto.setIdentificacion(transaccion.getUsuario().getIdentificacion());
            dto.setNombreUsuario(transaccion.getUsuario().getNombre());
            dto.setApellidoPaterno(transaccion.getUsuario().getApellidoPaterno());
        }
        if (transaccion.getEstado() != null){
            dto.setEstadoId(transaccion.getEstado().getIdEstado());
            dto.setNombreEstado(transaccion.getEstado().getNombre());
        }
        if (transaccion.getMovimientosAhorro() != null) {
            dto.setMovimientosAhorroId(transaccion.getMovimientosAhorro().getIdMovimiento());
            dto.setMontoAhorros(transaccion.getMovimientosAhorro().getMonto());
        }
        if (transaccion.getPagosPrestamos() != null){
            dto.setPagosPrestamosId(transaccion.getPagosPrestamos().getIdPago());
            dto.setMontoAbonadoPagoPrestamo(transaccion.getPagosPrestamos().getMontoAbonado());
        }
        return dto;
    }

    @Override
    @Transactional
    public void registrarTransaccion(TransaccionDTO transaccionDTO) {
        transaccionStoredProcedureRepository.insertarTransaccion(transaccionDTO);
    }

    @Override
    @Transactional
    public void actualizarTransaccion(TransaccionDTO transaccionDTO) {
        transaccionStoredProcedureRepository.editarTransaccion(transaccionDTO);
    }

    @Override
    @Transactional
    public void eliminarTransaccion(TransaccionDTO transaccionDTO) {
        transaccionStoredProcedureRepository.eliminarTransaccion(transaccionDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransaccionDTO> findAll(Pageable pageable) {
        return transaccionRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer cantidadTransacciones(Long usuarioId) {
        return transaccionFunctionRepository.cantidadTransaccionesUsuario(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionDTO> top5Transacciones() {
        return transaccionFunctionRepository.top5Transacciones();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionDTO> historialTransacciones(Long idUsuario) {
        return transaccionFunctionRepository.historialTransacciones(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionDTO> historialTransaccionesVM() {
        return transaccionViewRepository.getHistorialTransacciones();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionDTO> listadoTransaccionesCompletos() {
        return transaccionViewRepository.getTransaccionesCompletas();
    }
}
