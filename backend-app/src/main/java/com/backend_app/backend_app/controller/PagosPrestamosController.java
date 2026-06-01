package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.PagosPrestamos;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.PagosPrestamosDTO;
import com.backend_app.backend_app.service.PagosPrestamosService;
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
@RequestMapping("/api/pagos-prestamos")
public class PagosPrestamosController {

    @Autowired
    private PagosPrestamosService pagosPrestamosService;

    @GetMapping
    public List<PagosPrestamosDTO> listadoPagosPrestamos(){
        return pagosPrestamosService.listadoPagosPrestamos();
    }

    @GetMapping("/{idPago}")
    public ResponseEntity<PagosPrestamosDTO> buscarPagoPrestamoPorId(@PathVariable long idPago){
        Optional<PagosPrestamosDTO> pagoPrestamoOptional = pagosPrestamosService.buscarPagoPrestamoPorId(idPago);
        if (pagoPrestamoOptional.isPresent()){
            return ResponseEntity.ok(pagoPrestamoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<PagosPrestamosDTO> registrarPagoPrestamo(@RequestBody PagosPrestamosDTO pagoDTO) {
        pagosPrestamosService.registrarPagoPrestamo(pagoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<PagosPrestamosDTO> actualizarPago(@RequestBody PagosPrestamosDTO pagoDTO) {
        try {
            pagosPrestamosService.actualizarPagoPrestamo(pagoDTO);
            return ResponseEntity.ok(pagoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page/{page}")
    public Page<PagosPrestamosDTO> listPagoPrestamoPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 20);
        return pagosPrestamosService.findAll(pageable);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<PagosPrestamosDTO>> listadoPagosPrestamosCompletos(){
        List<PagosPrestamosDTO> pagosPrestamos = pagosPrestamosService.listadoPagosPrestamosCompletos();
        return ResponseEntity.ok(pagosPrestamos);
    }


}
