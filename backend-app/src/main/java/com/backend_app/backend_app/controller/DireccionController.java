package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.Direccion;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.DireccionDTO;
import com.backend_app.backend_app.service.DireccionService;
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
@RequestMapping("/api/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public List<DireccionDTO> listadoDirecciones(){
        return direccionService.listadoDirecciones();
    }

    @GetMapping("/{idDireccion}")
    public ResponseEntity<DireccionDTO> buscarDireccionPorId(@PathVariable long idDireccion){
        Optional<DireccionDTO> direccionOptional = direccionService.buscarDireccionPorId(idDireccion);
        if (direccionOptional.isPresent()){
            return ResponseEntity.ok(direccionOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<DireccionDTO> registrarDireccion(@RequestBody DireccionDTO direccionDTO){
        direccionService.insertarDireccion(direccionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<DireccionDTO> actualizarDireccion(@RequestBody DireccionDTO direccionDTO){
        try {
            direccionService.actualizarDireccion(direccionDTO);
            return ResponseEntity.ok(direccionDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idDireccion}")
    public ResponseEntity<CategoriaDTO> eliminarDireccion(@PathVariable long idDireccion){
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setIdDireccion(idDireccion);
        direccionService.eliminarDireccion(direccionDTO);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/page/{page}")
    public Page<DireccionDTO> listDireccionPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return direccionService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DireccionDTO>> buscarDireccionPorProvincia(@RequestParam String provincia){
        List<DireccionDTO> direcciones = direccionService.buscarDireccionPorProvincia(provincia);
        return ResponseEntity.ok(direcciones);
    }

    @GetMapping("/completas")
    public ResponseEntity<List<DireccionDTO>> listadoDireccionesCompletas(){
        List<DireccionDTO> direcciones = direccionService.listadoDireccionesCompletas();
        return ResponseEntity.ok(direcciones);
    }

}
