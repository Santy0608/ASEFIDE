package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.MovimientosAhorro;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.MovimientosAhorroDTO;
import com.backend_app.backend_app.service.MovimientoAhorroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/movimientos-ahorros")
public class MovimientoAhorroController {

    @Autowired
    private MovimientoAhorroService movimientoAhorroService;

    @GetMapping
    public List<MovimientosAhorroDTO> listadoMovimientosAhorro(){
        return movimientoAhorroService.listadoMovimientosAhorro();
    }

    @GetMapping("/{idMovimiento}")
    public ResponseEntity<MovimientosAhorroDTO> buscarMovimientoAhorro(long idMovimiento){
        Optional<MovimientosAhorroDTO> movimientoAhorroOptional = movimientoAhorroService.buscarMovimientoAhorroPorId(idMovimiento);
        if (movimientoAhorroOptional.isPresent()){
            return ResponseEntity.ok(movimientoAhorroOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<MovimientosAhorroDTO> registrarMovimiento(@RequestBody MovimientosAhorroDTO movimientoDTO) {
        // Ejecución delegada al servicio de negocio
        movimientoAhorroService.nsertarMovimientoAhorro(movimientoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<MovimientosAhorroDTO> actualizarMovimiento(@RequestBody MovimientosAhorroDTO movimientoDTO) {
        try {
            movimientoAhorroService.actualizarMovimientoAhorro(movimientoDTO);
            return ResponseEntity.ok(movimientoDTO);
        } catch (Exception e) {
            // El manejo de excepciones a nivel de controlador garantiza estabilidad en la respuesta
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page/{page}")
    public Page<MovimientosAhorroDTO> listMovimientoAhorroPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 20);
        return movimientoAhorroService.findAll(pageable);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<MovimientosAhorroDTO>> listadoMovimientosAhorrosCompletos(){
        List<MovimientosAhorroDTO> movimientosAhorros = movimientoAhorroService.listadoMovimientosAhorroCompleots();
        return ResponseEntity.ok(movimientosAhorros);
    }

}
