package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Transaccion;
import com.backend_app.backend_app.dto.TransaccionDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TransaccionService {

    List<TransaccionDTO> listadoTransacciones();

    Optional<TransaccionDTO> buscarTransaccionPorId(long idTransaccioin);

    TransaccionDTO convertirADTO(Transaccion transaccion);

    void registrarTransaccion(TransaccionDTO transaccionDTO);

    void actualizarTransaccion(TransaccionDTO transaccionDTO);

    void eliminarTransaccion(TransaccionDTO transaccionDTO);

    Page<TransaccionDTO> findAll(Pageable pageable);

    Integer cantidadTransacciones(Long usuarioId);

    List<TransaccionDTO> top5Transacciones();

    List<TransaccionDTO> historialTransacciones(Long idUsuario);

    List<TransaccionDTO> historialTransaccionesVM();

    List<TransaccionDTO> listadoTransaccionesCompletos();

}
