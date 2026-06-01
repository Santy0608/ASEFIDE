package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.dto.CuentasAhorroDTO;
import com.backend_app.backend_app.dto.RolDTO;
import com.backend_app.backend_app.dto.TransaccionDTO;
import com.backend_app.backend_app.service.CuentasAhorroService;
import com.backend_app.backend_app.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cuentas-ahorro")
public class CuentasAhorroController {

    @Autowired
    private CuentasAhorroService cuentasAhorroService;

    @GetMapping
    public List<CuentasAhorroDTO> listadoCuentasAhorro(){
        return cuentasAhorroService.listadoCuentasAhorro();
    }

    @GetMapping("/total")
    public ResponseEntity<Double> sumaTotalAhorros(){
        Double sumaTotal = cuentasAhorroService.sumaTotalAhorros();
        return ResponseEntity.ok(sumaTotal);
    }

    @GetMapping("/promedio-ahorros")
    public ResponseEntity<Double> promedioAhorros(){
        Double promedioAhorros = cuentasAhorroService.promedioAhorros();;
        return ResponseEntity.ok(promedioAhorros);
    }

    @GetMapping("/{idAhorro}")
    public ResponseEntity<CuentasAhorroDTO> buscarCuentasAhorroPorId(@PathVariable long idAhorro){
        Optional<CuentasAhorroDTO> cuentasAhorroOptional = cuentasAhorroService.buscarCuentasAhorroPorId(idAhorro);
        if (cuentasAhorroOptional.isPresent()){
            return ResponseEntity.ok(cuentasAhorroOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<CuentasAhorroDTO> registrarCuentaAhorro(@RequestBody CuentasAhorroDTO cuentasAhorroDTO){
        cuentasAhorroService.insertarCuentaAhorro(cuentasAhorroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentasAhorroDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<CuentasAhorroDTO> actualizarCuentaAhorro(@RequestBody CuentasAhorroDTO cuentasAhorroDTO){
        try {
            cuentasAhorroService.actualizarCuentasAhorro(cuentasAhorroDTO);
            return ResponseEntity.ok(cuentasAhorroDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idAhorro}")
    public ResponseEntity<CuentasAhorroDTO> eliminarCuentaAhorro(@PathVariable long idAhorro){
        CuentasAhorroDTO cuentasAhorroDTO = new CuentasAhorroDTO();
        cuentasAhorroDTO.setIdAhorro(idAhorro);
        cuentasAhorroService.eliminarCuentasAhorro(cuentasAhorroDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<CuentasAhorroDTO> listCuentasAhorroPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 25);
        return cuentasAhorroService.findAll(pageable);
    }

    @GetMapping("/mayor")
    public ResponseEntity<List<CuentasAhorroDTO>> usuariosMayorAhorro(
            @RequestParam BigDecimal monto) {

        List<CuentasAhorroDTO> usuarios = cuentasAhorroService.usuariosMayorAhorro(monto);

        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/reporte/{idUsuario}")
    public ResponseEntity<List<CuentasAhorroDTO>> reporteAhorrosUsuario(
            @PathVariable Long idUsuario) {

        List<CuentasAhorroDTO> reporte = cuentasAhorroService.reporteAhorrosUsuario(idUsuario);

        if (reporte == null || reporte.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/top10-mas")
    public ResponseEntity<List<CuentasAhorroDTO>> top10MasAhorros() {
        List<CuentasAhorroDTO> top = cuentasAhorroService.top10MasAhorros();

        if (top == null || top.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(top);
    }


    @GetMapping("/top10-menos")
    public ResponseEntity<List<CuentasAhorroDTO>> top10MenosAhorros() {
        List<CuentasAhorroDTO> top = cuentasAhorroService.top10MenosAhorros();

        if (top == null || top.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(top);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<CuentasAhorroDTO>> listadoCuentasAhorrosCompletos(){
        List<CuentasAhorroDTO> cuentasAhorros = cuentasAhorroService.listadoCuentasAhorrosCompletos();
        return ResponseEntity.ok(cuentasAhorros);
    }



}
