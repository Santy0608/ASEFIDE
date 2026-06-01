package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.Estado;
import com.backend_app.backend_app.domain.Prestamo;
import com.backend_app.backend_app.dto.BeneficioDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.dto.PrestamoDTO;
import com.backend_app.backend_app.service.PrestamoService;
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
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public List<PrestamoDTO> listadoPrestamos(){
        return prestamoService.listadoPrestamos();
    }

    @GetMapping("/{idPrestamo}")
    public ResponseEntity<PrestamoDTO> buscarPrestamoPorId(@PathVariable long idPrestamo){
        Optional<PrestamoDTO> prestamoOptional = prestamoService.buscarPrestamoPorId(idPrestamo);
        if (prestamoOptional.isPresent()){
            return ResponseEntity.ok(prestamoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<PrestamoDTO> registrarPrestamo(@RequestBody PrestamoDTO prestamoDTO){
        prestamoService.insertarPrestamo(prestamoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<PrestamoDTO> actualizarPrestamo(@RequestBody PrestamoDTO prestamoDTO){
        try {
            prestamoService.actualizarPrestamo(prestamoDTO);
            return ResponseEntity.ok(prestamoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idPrestamo}")
    public ResponseEntity<PrestamoDTO> eliminarPrestamo(@PathVariable long idPrestamo){
        PrestamoDTO prestamoDTO = new PrestamoDTO();
        prestamoDTO.setIdPrestamo(idPrestamo);
        prestamoService.eliminarPrestamo(prestamoDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<PrestamoDTO> listPrestamoPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 20);
        return prestamoService.findAll(pageable);
    }

    @GetMapping("/estado")
    public ResponseEntity<List<EstadoDTO>> getEstadoPrestamos() {
        List<EstadoDTO> data = prestamoService.obtenerEstadosPrestamos();
        return data.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(data);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<PrestamoDTO>> listadoPrestamosCompletos(){
        List<PrestamoDTO> prestamos = prestamoService.listadoPrestamosCompletos();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/por-estado")
    public ResponseEntity<List<EstadoDTO>> prestamosPorEstado(){
        List<EstadoDTO> estados = prestamoService.prestamosPorEstado();
        if (estados == null || estados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estados);
    }

}
