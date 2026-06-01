package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.DetalleTransaccion;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.DetalleTransaccionDTO;
import com.backend_app.backend_app.service.DetalleTransaccionService;
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
@RequestMapping("/api/detalle-transaccion")
public class DetalleTransaccionController {

    @Autowired
    private DetalleTransaccionService detalleTransaccionService;

    @GetMapping
    public List<DetalleTransaccionDTO> listadoDetallesTransaccion(){
        return detalleTransaccionService.listadoDetallesTransaccion();
    }

    @GetMapping("/{idDetalle}")
    public ResponseEntity<DetalleTransaccionDTO> buscarDetalleTransaccionPorId(@PathVariable long idDetalle){
        Optional<DetalleTransaccionDTO> detalleTransaccionOptional = detalleTransaccionService.buscarDetalleTransaccionPorId(idDetalle);
        if (detalleTransaccionOptional.isPresent()){
            ResponseEntity.ok(detalleTransaccionOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<DetalleTransaccionDTO> registrarDetalle(@RequestBody DetalleTransaccionDTO detalleDTO) {
        detalleTransaccionService.registrarDetalleTransaccion(detalleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<DetalleTransaccionDTO> actualizarDetalle(@RequestBody DetalleTransaccionDTO detalleDTO) {
        try {
            detalleTransaccionService.actualizarDetalleTransaccion(detalleDTO);
            return ResponseEntity.ok(detalleDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page/{page}")
    public Page<DetalleTransaccionDTO> listDetalleTransaccionPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return detalleTransaccionService.findAll(pageable);
    }

    @GetMapping("/completas")
    public ResponseEntity<List<DetalleTransaccionDTO>> listadoDetallesTransaccionesCompletas(){
        List<DetalleTransaccionDTO> detallesTransacciones = detalleTransaccionService.ListadoDetallesTransaccionesCompletas();
        return ResponseEntity.ok(detallesTransacciones);
    }

}
