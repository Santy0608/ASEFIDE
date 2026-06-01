package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.TipoTransaccion;
import com.backend_app.backend_app.dto.TipoReporteDTO;
import com.backend_app.backend_app.dto.TipoTransaccionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TipoTransaccionService {

    List<TipoTransaccionDTO> listadoTransacciones();

    Optional<TipoTransaccionDTO> buscarTipoTransaccionPorId(long idTipoTransaccion);

    TipoTransaccionDTO convertirADTO(TipoTransaccion tipoTransaccion);

    void insertarTipoTransaccion(TipoTransaccionDTO tipoTransaccionDTO);

    void actualizarTipoTransaccion(TipoTransaccionDTO tipoTransaccionDTO);

    void eliminarTipoTransaccion(TipoTransaccionDTO tipoTransaccionDTO);

    Page<TipoTransaccionDTO> findAll(Pageable pageable);

    List<TipoTransaccionDTO> buscarTipoTransaccionPorNombre(String nombreTipoTransaccion);

    List<TipoTransaccionDTO> listadoTiposTransaccionesCompleatas();
}
