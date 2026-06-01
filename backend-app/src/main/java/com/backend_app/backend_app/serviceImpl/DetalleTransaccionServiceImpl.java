package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.DetalleTransaccionRepository;
import com.backend_app.backend_app.domain.DetalleTransaccion;
import com.backend_app.backend_app.dto.DetalleTransaccionDTO;
import com.backend_app.backend_app.repository.DetalleTransaccionStoredProcedureRepository;
import com.backend_app.backend_app.service.DetalleTransaccionService;
import com.backend_app.backend_app.views.DetalleTransaccionViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetalleTransaccionServiceImpl implements DetalleTransaccionService {

    @Autowired
    private DetalleTransaccionRepository detalleTransaccionRepository;

    @Autowired
    private DetalleTransaccionStoredProcedureRepository detalleTransaccionStoredProcedureRepository;

    @Autowired
    private DetalleTransaccionViewRepository detalleTransaccionViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleTransaccionDTO> listadoDetallesTransaccion() {
        List<DetalleTransaccion> detalleTransacciones = detalleTransaccionRepository.findAll();
        List<DetalleTransaccionDTO> dtos = detalleTransacciones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleTransaccionDTO> buscarDetalleTransaccionPorId(long idDetalle) {
        return Optional.empty();
    }

    @Override
    public DetalleTransaccionDTO convertirADTO(DetalleTransaccion detalleTransaccion) {
        DetalleTransaccionDTO dto = new DetalleTransaccionDTO();
        dto.setIdDetalle(detalleTransaccion.getIdDetalle());
        if (detalleTransaccion.getTransaccion() != null){
            dto.setTransaccionId(detalleTransaccion.getIdDetalle());
            dto.setMontoTotalTransaccion(detalleTransaccion.getTransaccion().getMontoTotal());
        }
        dto.setConcepto(detalleTransaccion.getConcepto());
        dto.setSubTotal(detalleTransaccion.getSubTotal());
        return dto;
    }

    @Override
    @Transactional
    public void registrarDetalleTransaccion(DetalleTransaccionDTO detalleTransaccionDTO) {
        detalleTransaccionStoredProcedureRepository.registrarDetalleTransaccion(detalleTransaccionDTO);
    }

    @Override
    @Transactional
    public void actualizarDetalleTransaccion(DetalleTransaccionDTO detalleTransaccionDTO) {
        detalleTransaccionStoredProcedureRepository.editarDetalleTransaccion(detalleTransaccionDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetalleTransaccionDTO> findAll(Pageable pageable) {
        return detalleTransaccionRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleTransaccionDTO> ListadoDetallesTransaccionesCompletas() {
        return detalleTransaccionViewRepository.getDetallesTranasaccionesCompletas();
    }


}
