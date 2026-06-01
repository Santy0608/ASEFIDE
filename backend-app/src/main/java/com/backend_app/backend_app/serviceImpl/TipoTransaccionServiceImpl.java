package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.TipoReporteRepository;
import com.backend_app.backend_app.dao.TipoTransaccionRepository;
import com.backend_app.backend_app.domain.TipoTransaccion;
import com.backend_app.backend_app.dto.TipoTransaccionDTO;
import com.backend_app.backend_app.functions.TipoTransaccionFunctionRepository;
import com.backend_app.backend_app.repository.TipoTransaccionProceduredRepository;
import com.backend_app.backend_app.service.TipoTransaccionService;
import com.backend_app.backend_app.views.TipoTransaccionViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoTransaccionServiceImpl implements TipoTransaccionService {

    @Autowired
    private TipoTransaccionRepository tipoTransaccionRepository;

    @Autowired
    private TipoTransaccionProceduredRepository tipoTransaccionProceduredRepository;

    @Autowired
    private TipoTransaccionFunctionRepository tipoTransaccionFunctionRepository;

    @Autowired
    private TipoTransaccionViewRepository tipoTransaccionViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoTransaccionDTO> listadoTransacciones() {
        List<TipoTransaccion> tipoTransacciones = tipoTransaccionRepository.findAll();
        List<TipoTransaccionDTO> dtos = tipoTransacciones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoTransaccionDTO> buscarTipoTransaccionPorId(long idTipoTransaccion) {
        return tipoTransaccionRepository.findById(idTipoTransaccion).map(this::convertirADTO);
    }

    @Override
    public TipoTransaccionDTO convertirADTO(TipoTransaccion tipoTransaccion) {
        TipoTransaccionDTO dto = new TipoTransaccionDTO();
        dto.setIdTipoTransaccion(tipoTransaccion.getIdTipoTransaccion());
        dto.setNombre(tipoTransaccion.getNombre());
        dto.setDescripcion(tipoTransaccion.getDescripcion());
        if (tipoTransaccion.getEstado() != null){
            dto.setEstadoId(tipoTransaccion.getEstado().getIdEstado());
            dto.setNombreEstado(tipoTransaccion.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarTipoTransaccion(TipoTransaccionDTO tipoTransaccionDTO) {
        tipoTransaccionProceduredRepository.insertarTipoTransaccion(tipoTransaccionDTO);
    }

    @Override
    @Transactional
    public void actualizarTipoTransaccion(TipoTransaccionDTO tipoTransaccionDTO) {
        tipoTransaccionProceduredRepository.editarTipoTransaccion(tipoTransaccionDTO);
    }

    @Override
    @Transactional
    public void eliminarTipoTransaccion(TipoTransaccionDTO tipoTransaccionDTO) {
        tipoTransaccionProceduredRepository.eliminarTipoTransaccion(tipoTransaccionDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoTransaccionDTO> findAll(Pageable pageable) {
        return tipoTransaccionRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoTransaccionDTO> buscarTipoTransaccionPorNombre(String nombreTipoTransaccion) {
        return tipoTransaccionFunctionRepository.buscarTipoTransaccionPorNombre(nombreTipoTransaccion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoTransaccionDTO> listadoTiposTransaccionesCompleatas() {
        return tipoTransaccionViewRepository.getTiposTransaccionesCompletas();
    }

}
