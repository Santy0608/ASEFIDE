package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.DetalleTransaccion;
import com.backend_app.backend_app.dto.DetalleTransaccionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DetalleTransaccionService {

     List<DetalleTransaccionDTO> listadoDetallesTransaccion();

     Optional<DetalleTransaccionDTO> buscarDetalleTransaccionPorId(long idDetalle);

     DetalleTransaccionDTO convertirADTO(DetalleTransaccion detalleTransaccion);

     void registrarDetalleTransaccion(DetalleTransaccionDTO detalleTransaccionDTO);

     void actualizarDetalleTransaccion(DetalleTransaccionDTO detalleTransaccionDTO);

     Page<DetalleTransaccionDTO> findAll(Pageable pageable);

     List<DetalleTransaccionDTO> ListadoDetallesTransaccionesCompletas();

}
