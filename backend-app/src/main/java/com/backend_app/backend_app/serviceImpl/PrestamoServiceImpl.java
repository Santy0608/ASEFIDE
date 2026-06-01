package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.PrestamoRepository;
import com.backend_app.backend_app.domain.Estado;
import com.backend_app.backend_app.domain.Prestamo;
import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.dto.PrestamoDTO;
import com.backend_app.backend_app.repository.PrestamoStoredProcedureRepository;
import com.backend_app.backend_app.service.PrestamoService;
import com.backend_app.backend_app.views.PrestamoViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoStoredProcedureRepository prestamoStoredProcedureRepository;

    @Autowired
    private PrestamoViewRepository prestamoViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoDTO> listadoPrestamos() {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        List<PrestamoDTO> dtos = prestamos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrestamoDTO> buscarPrestamoPorId(long idPrestamo) {
        return prestamoRepository.findById(idPrestamo).map(this::convertirADTO);
    }

    @Override
    public PrestamoDTO convertirADTO(Prestamo prestamo) {
        PrestamoDTO dto = new PrestamoDTO();
        dto.setIdPrestamo(prestamo.getIdPrestamo());
        if (prestamo.getUsuario() != null){
            dto.setUsuarioId(prestamo.getUsuario().getIdUsuario());
            dto.setIdentificacion(prestamo.getUsuario().getIdentificacion());
            dto.setNombreUsuario(prestamo.getUsuario().getNombre());
            dto.setApellidoPaterno(prestamo.getUsuario().getApellidoPaterno());
        }
        dto.setMontoSolicitado(prestamo.getMontoSolicitado());
        dto.setFechaAprobacion(prestamo.getFechaAprobacion());
        dto.setSaldoPendiente(prestamo.getSaldoPendiente());
        if (prestamo.getEstado() != null){
            dto.setEstadoId(prestamo.getEstado().getIdEstado());
            dto.setNombreEstado(prestamo.getEstado().getNombre());
        }
        dto.setTasaIntereses(prestamo.getTasaIntereses());
        dto.setPlazoMeses(prestamo.getPlazoMeses());
        return dto;
    }

    @Override
    @Transactional
    public void insertarPrestamo(PrestamoDTO prestamoDTO) {
        prestamoStoredProcedureRepository.registrarPrestamo(prestamoDTO);
    }

    @Override
    @Transactional
    public void actualizarPrestamo(PrestamoDTO prestamoDTO) {
        prestamoStoredProcedureRepository.editarPrestamo(prestamoDTO);
    }

    @Override
    @Transactional
    public void eliminarPrestamo(PrestamoDTO prestamoDTO) {
        prestamoStoredProcedureRepository.eliminarPrestamo(prestamoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrestamoDTO> findAll(Pageable pageable) {
        return prestamoRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDTO> obtenerEstadosPrestamos() {
        return prestamoViewRepository.getEstadoPrestamos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoDTO> listadoPrestamosCompletos() {
        return prestamoViewRepository.getPrestamosCompletos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDTO> prestamosPorEstado() {
        return prestamoViewRepository.getEstadoPrestamos();
    }
}
