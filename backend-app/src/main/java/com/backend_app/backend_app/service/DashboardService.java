package com.backend_app.backend_app.service;

import com.backend_app.backend_app.dto.*;

import java.util.List;

public interface DashboardService {

    public DashboardKpiDTO obtenerKPIs();

    List<DashboardTransaccionDTO> obtenerDashboardTransacciones();

    List<DashboardTipoAhorroDTO> obtenerDashboardTiposAhorros();

    List<DashboardPrestamosEstadoDTO> obtenerDashboardPrestamosEstado();

    List<DashboardActividadesProximasDTO> obtenerDashboardActividadesProximas();



}
