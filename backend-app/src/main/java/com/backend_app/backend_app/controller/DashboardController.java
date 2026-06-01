package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.dto.*;
import com.backend_app.backend_app.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/Kpis")
    public ResponseEntity<DashboardKpiDTO> getKPIs(){
        return ResponseEntity.ok(dashboardService.obtenerKPIs());
    }

    @GetMapping("/transacciones")
    public ResponseEntity<List<DashboardTransaccionDTO>> obtenerListadoTransacciones(){
        List<DashboardTransaccionDTO> transacciones = dashboardService.obtenerDashboardTransacciones();
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/tipos-ahorros")
    public ResponseEntity<List<DashboardTipoAhorroDTO>> obtenerListadoTiposAhorros(){
        List<DashboardTipoAhorroDTO> tiposAhorros = dashboardService.obtenerDashboardTiposAhorros();
        return ResponseEntity.ok(tiposAhorros);
    }

    @GetMapping("/prestamos-estado")
    public ResponseEntity<List<DashboardPrestamosEstadoDTO>> obtenerListadoPrestamosEstado(){
        List<DashboardPrestamosEstadoDTO> prestamosEstados = dashboardService.obtenerDashboardPrestamosEstado();
        return ResponseEntity.ok(prestamosEstados);
    }

    @GetMapping("/actividades-proximas")
    public ResponseEntity<List<DashboardActividadesProximasDTO>> obtenerListadoActividadesProximas(){
        List<DashboardActividadesProximasDTO> actividadesProximas = dashboardService.obtenerDashboardActividadesProximas();
        return ResponseEntity.ok(actividadesProximas);
    }

}
