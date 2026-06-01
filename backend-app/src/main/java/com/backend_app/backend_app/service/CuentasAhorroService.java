package com.backend_app.backend_app.service;


import com.backend_app.backend_app.domain.CuentasAhorro;
import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import com.backend_app.backend_app.dto.TransaccionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CuentasAhorroService {

    List<CuentasAhorroDTO> listadoCuentasAhorro();

    Optional<CuentasAhorroDTO> buscarCuentasAhorroPorId(long idCuentasAhorro);

    CuentasAhorroDTO convertirADTO(CuentasAhorro cuentasAhorro);

    void insertarCuentaAhorro(CuentasAhorroDTO cuentasAhorroDTO);

    void actualizarCuentasAhorro(CuentasAhorroDTO cuentasAhorroDTO);

    void eliminarCuentasAhorro(CuentasAhorroDTO cuentasAhorroDTO);

    Page<CuentasAhorroDTO> findAll(Pageable pageable);

    Double sumaTotalAhorros();

    Double promedioAhorros();

    List<CuentasAhorroDTO> reporteAhorrosUsuario(Long idUsuario);

    List<CuentasAhorroDTO> usuariosMayorAhorro(BigDecimal monto);

    List<CuentasAhorroDTO> top10MasAhorros();

    List<CuentasAhorroDTO> top10MenosAhorros();

    List<CuentasAhorroDTO> listadoCuentasAhorrosCompletos();

}
