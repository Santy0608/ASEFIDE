package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.DireccionRepository;
import com.backend_app.backend_app.domain.Direccion;
import com.backend_app.backend_app.dto.DireccionDTO;
import com.backend_app.backend_app.functions.DireccionFunctionRepository;
import com.backend_app.backend_app.repository.DireccionStoredProcedureRepository;
import com.backend_app.backend_app.service.DireccionService;
import com.backend_app.backend_app.views.DireccionViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DireccionServiceImpl implements DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private DireccionStoredProcedureRepository direccionStoredProcedureRepository;

    @Autowired
    private DireccionFunctionRepository direccionFunctionRepository;

    @Autowired
    private DireccionViewRepository direccionViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DireccionDTO> listadoDirecciones() {
        List<Direccion> direcciones = direccionRepository.findAll();
        List<DireccionDTO> dtos = direcciones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DireccionDTO> buscarDireccionPorId(long idDireccion) {
        return direccionRepository.findById(idDireccion).map(this::convertirADTO);
    }

    @Override
    public DireccionDTO convertirADTO(Direccion direccion) {
        DireccionDTO dto = new DireccionDTO();
        dto.setIdDireccion(direccion.getIdDireccion());
        dto.setProvincia(direccion.getProvincia());
        dto.setCanton(direccion.getCanton());
        dto.setDistrito(direccion.getDistrito());
        if (direccion.getEstado() != null){
            dto.setEstadoId(direccion.getEstado().getIdEstado());
            dto.setNombreEstado(direccion.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void insertarDireccion(DireccionDTO direccionDTO) {
        direccionStoredProcedureRepository.insertarDireccion(direccionDTO);
    }

    @Override
    @Transactional
    public void actualizarDireccion(DireccionDTO direccionDTO) {
        direccionStoredProcedureRepository.editarDireccion(direccionDTO);
    }

    @Override
    @Transactional
    public void eliminarDireccion(DireccionDTO direccionDTO) {
        direccionStoredProcedureRepository.eliminarDireccion(direccionDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DireccionDTO> findAll(Pageable pageable) {
        return direccionRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DireccionDTO> buscarDireccionPorProvincia(String provincia) {
        return direccionFunctionRepository.buscarDireccionPorProvincia(provincia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DireccionDTO> listadoDireccionesCompletas() {
        return direccionViewRepository.getDireccionesCompletas();
    }
}
