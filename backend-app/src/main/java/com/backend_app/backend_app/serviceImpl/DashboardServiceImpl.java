package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dto.*;
import com.backend_app.backend_app.service.DashboardService;
import com.backend_app.backend_app.views.DashboardViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {


    @Autowired
    private DashboardViewRepository dashboardViewRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardKpiDTO obtenerKPIs() {
        return dashboardViewRepository.getDashboardKPIs();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DashboardTransaccionDTO> obtenerDashboardTransacciones() {
        return dashboardViewRepository.getDashboardTransacciones();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DashboardTipoAhorroDTO> obtenerDashboardTiposAhorros() {
        return dashboardViewRepository.getResumenTiposAhorro();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DashboardPrestamosEstadoDTO> obtenerDashboardPrestamosEstado() {
        return dashboardViewRepository.getDashboardPrestamosEstado();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DashboardActividadesProximasDTO> obtenerDashboardActividadesProximas() {
        return dashboardViewRepository.getDashboardActividadesProximas();
    }



}
