package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.Estado;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.EstadoDTO;
import com.backend_app.backend_app.service.EstadoService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

@RequestMapping("/api/estados")
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public List<EstadoDTO> listadoEstados(){
        return estadoService.listadoEstados();
    }

    @GetMapping("/{idEstado}")
    public ResponseEntity<EstadoDTO> buscarEstadoPorId(@PathVariable Long idEstado){
        Optional<EstadoDTO> estadoOptional = estadoService.buscarEstadoPorId(idEstado);
        if (estadoOptional.isPresent()){
            return ResponseEntity.ok(estadoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<EstadoDTO> registrarEstado(@RequestBody EstadoDTO estadoDTO){
        estadoService.insertarEstado(estadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<EstadoDTO> actualizarEstado(@RequestBody EstadoDTO estadoDTO){
        try {
            estadoService.actualizarEstado(estadoDTO);
            return ResponseEntity.ok(estadoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page/{page}")
    public Page<EstadoDTO> listEstadoPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return estadoService.findAll(pageable);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<EstadoDTO>> listadoEstadosCompletos(){
        List<EstadoDTO> estados = estadoService.listadoEstadosCompletos();
        return ResponseEntity.ok(estados);
    }


}
