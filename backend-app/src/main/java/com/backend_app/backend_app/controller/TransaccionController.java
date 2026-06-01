package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.Transaccion;
import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.TransaccionDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import com.backend_app.backend_app.service.TransaccionService;
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
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping
    public List<TransaccionDTO> listadoTransacciones() {
        return transaccionService.listadoTransacciones();
    }

    @GetMapping("/cantidad/{usuarioId}")
    public ResponseEntity<Integer> cantidadTransacciones(@PathVariable Long usuarioId) {
        Integer cantidad = transaccionService.cantidadTransacciones(usuarioId);
        if (cantidad < 0) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(cantidad);
    }

    @GetMapping("/{idTransaccion}")
    public ResponseEntity<TransaccionDTO> buscarTransaccionPorId(@PathVariable long idTransaccion) {
        Optional<TransaccionDTO> transaccionOptional = transaccionService.buscarTransaccionPorId(idTransaccion);
        if (transaccionOptional.isPresent()) {
            return ResponseEntity.ok(transaccionOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<TransaccionDTO> registrarTransaccion(@RequestBody TransaccionDTO transaccionDTO) {
        transaccionService.registrarTransaccion(transaccionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaccionDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<TransaccionDTO> actualizarTransaccion(@RequestBody TransaccionDTO transaccionDTO) {
        try {
            transaccionService.actualizarTransaccion(transaccionDTO);
            return ResponseEntity.ok(transaccionDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idTransaccion}")
    public ResponseEntity<ActividadDTO> eliminarTransaccion(@PathVariable long idTransaccion) {
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        transaccionDTO.setIdTransaccion(idTransaccion);
        transaccionService.eliminarTransaccion(transaccionDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<TransaccionDTO> listTransaccionPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return transaccionService.findAll(pageable);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<TransaccionDTO>> top5Transacciones() {
        List<TransaccionDTO> top = transaccionService.top5Transacciones();

        if (top == null || top.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(top);
    }

    @GetMapping("/historial/{idUsuario}")
    public ResponseEntity<List<TransaccionDTO>> historialTransacciones(
            @PathVariable Long idUsuario) {

        List<TransaccionDTO> historial = transaccionService.historialTransacciones(idUsuario);

        if (historial == null || historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/historial-transacciones")
    public ResponseEntity<List<TransaccionDTO>> historialTransaccionesVM(){
        List<TransaccionDTO> transascciones = transaccionService.historialTransaccionesVM();
        return ResponseEntity.ok(transascciones);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<TransaccionDTO>> listadoTransaccionesCompletos(){
        List<TransaccionDTO> transacciones = transaccionService.listadoTransaccionesCompletos();
        return ResponseEntity.ok(transacciones);
    }

}
