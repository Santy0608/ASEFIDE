package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.TelefonoDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import com.backend_app.backend_app.service.TelefonoService;
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
@RequestMapping("/api/telefonos")
public class TelefonoController {

    @Autowired
    private TelefonoService telefonoService;

    @GetMapping
    public List<TelefonoDTO> listadoTelefonos(){
        return telefonoService.listadoTelefonos();
    }

    @GetMapping("/{idTelefono}")
    public ResponseEntity<TelefonoDTO> buscarTelefonoPorId(@PathVariable long idTelefono){
        Optional<TelefonoDTO> telefonoOptional = telefonoService.buscarTelefonoPorId(idTelefono);
        if (telefonoOptional.isPresent()){
            return ResponseEntity.ok(telefonoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<TelefonoDTO> insertarTelefono(@RequestBody TelefonoDTO telefonoDTO){
        telefonoService.insertarTelefono(telefonoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(telefonoDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<TelefonoDTO> actualizarTelefono(@RequestBody TelefonoDTO telefonoDTO){
        try {
            telefonoService.actualizarTelefono(telefonoDTO);
            return ResponseEntity.ok(telefonoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idTelefono}")
    public ResponseEntity<TelefonoDTO> eliminarTelefono(@PathVariable long idTelefono){
        TelefonoDTO telefonoDTO = new TelefonoDTO();
        telefonoDTO.setIdTelefono(idTelefono);
        telefonoService.eliminarTelefono(telefonoDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<TelefonoDTO> listTelefonoPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 50);
        return telefonoService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TelefonoDTO>> buscarNumeroTelefono(@RequestParam String numeroTelefono){
        List<TelefonoDTO> telefonos = telefonoService.buscarNumeroTelefono(numeroTelefono);
        return ResponseEntity.ok(telefonos);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<TelefonoDTO>> listadoTelefonosCompletos(){
        List<TelefonoDTO> telefonos = telefonoService.listadoTelefonosCompletos();
        return ResponseEntity.ok(telefonos);
    }

}
