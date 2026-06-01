package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.TelefonoDTO;
import com.backend_app.backend_app.service.CorreoService;
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
@RequestMapping("/api/correos")
public class CorreoController {

    @Autowired
    private CorreoService correoService;

    @GetMapping
    public List<CorreoDTO> listadoCorreos(){
        return correoService.listadoCorreos();
    }

    @GetMapping("/{idCorreo}")
    public ResponseEntity<CorreoDTO> buscarCorreoPorId(@PathVariable Long idCorreo){
        Optional<CorreoDTO> correoOptional = correoService.buscarCorreoPorId(idCorreo);
        if (correoOptional.isPresent()){
            return ResponseEntity.ok(correoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<CorreoDTO> insertarCorreo(@RequestBody CorreoDTO correoDTO){
        System.out.println("Correo a agregar: " + correoDTO);
        correoService.insertarCorreo(correoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(correoDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<CorreoDTO> actualizarCorreo(@RequestBody CorreoDTO correoDTO){
        try {
            correoService.actualizarCorreo(correoDTO);
            return ResponseEntity.ok(correoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idCorreo}")
    public ResponseEntity<CategoriaDTO> eliminarCorreo(@PathVariable long idCorreo){
        CorreoDTO correoDTO = new CorreoDTO();
        correoDTO.setIdCorreo(idCorreo);
        correoService.eliminarCorreo(correoDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<CorreoDTO> listCorreoPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return correoService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CorreoDTO>> buscarCorreoElectronico(@RequestParam String correoElectronico){
        List<CorreoDTO> correos = correoService.buscarCorreoElectronico(correoElectronico);
        return ResponseEntity.ok(correos);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<CorreoDTO>> listadoCorreosCompletos(){
        List<CorreoDTO> correos = correoService.listadoCorreosCompletos();
        return ResponseEntity.ok(correos);
    }

}
