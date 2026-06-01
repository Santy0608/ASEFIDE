package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.PagosPrestamosRepository;
import com.backend_app.backend_app.domain.PagosPrestamos;
import com.backend_app.backend_app.dto.PagosPrestamosDTO;
import com.backend_app.backend_app.repository.PagosPrestamosStoredProcedureRepository;
import com.backend_app.backend_app.service.PagosPrestamosService;
import com.backend_app.backend_app.views.PagosPrestamosViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagosPrestamosServiceImpl implements PagosPrestamosService {

    @Autowired
    private PagosPrestamosRepository pagosPrestamosRepository;

    @Autowired
    private PagosPrestamosStoredProcedureRepository pagosPrestamosStoredProcedureRepository;

    @Autowired
    private PagosPrestamosViewRepository pagosPrestamosViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PagosPrestamosDTO> listadoPagosPrestamos() {
        List<PagosPrestamos> pagosPrestamos = pagosPrestamosRepository.findAll();
        List<PagosPrestamosDTO> dtos = pagosPrestamos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PagosPrestamosDTO> buscarPagoPrestamoPorId(long idPagoPrestamo) {
        return pagosPrestamosRepository.findById(idPagoPrestamo).map(this::convertirADTO);
    }

    @Override
    public PagosPrestamosDTO convertirADTO(PagosPrestamos pagosPrestamos) {
        PagosPrestamosDTO dto = new PagosPrestamosDTO();
        if (pagosPrestamos.getPrestamo() != null){
            dto.setPrestamoId(pagosPrestamos.getPrestamo().getIdPrestamo());
            dto.setMontoSolicitado(pagosPrestamos.getPrestamo().getMontoSolicitado());
            dto.setFechaAprobacion(pagosPrestamos.getPrestamo().getFechaAprobacion());
            dto.setSaldoPendiente(pagosPrestamos.getPrestamo().getSaldoPendiente());
        }
        if (pagosPrestamos.getTransaccion() != null){
            dto.setTransaccionId(pagosPrestamos.getTransaccion().getIdTransaccion());
            dto.setMontoTotal(pagosPrestamos.getTransaccion().getMontoTotal());
        }
        dto.setMontoAbonado(pagosPrestamos.getMontoAbonado());
        dto.setFechaPago(pagosPrestamos.getFechaPago());
        return dto;
    }

    @Override
    @Transactional
    public void registrarPagoPrestamo(PagosPrestamosDTO pagosPrestamosDTO) {
        pagosPrestamosStoredProcedureRepository.registrarPagoPrestamo(pagosPrestamosDTO);
    }

    @Override
    @Transactional
    public void actualizarPagoPrestamo(PagosPrestamosDTO pagosPrestamosDTO) {
        pagosPrestamosStoredProcedureRepository.editarPagoPrestamo(pagosPrestamosDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PagosPrestamosDTO> findAll(Pageable pageable) {
        return pagosPrestamosRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagosPrestamosDTO> listadoPagosPrestamosCompletos() {
        return pagosPrestamosViewRepository.getPagosPrestamosCompletos();
    }

}
