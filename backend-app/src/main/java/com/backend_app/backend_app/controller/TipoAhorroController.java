package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.TipoAhorro;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.RolDTO;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.service.TipoAhorroService;
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
@RequestMapping("/api/tipos-ahorros")
public class TipoAhorroController {

    @Autowired
    private TipoAhorroService tipoAhorroService;

    @GetMapping
    public List<TipoAhorroDTO> listadoTiposAhorro(){
        return tipoAhorroService.listadoTiposAhorro();
    }

    @GetMapping("/{idTipoAhorro}")
    public ResponseEntity<TipoAhorroDTO> buscarTipoAhorroPorId(@PathVariable long idTipoAhorro){
        Optional<TipoAhorroDTO> tipoAhorroOptional = tipoAhorroService.buscarTipoAhorroPorId(idTipoAhorro);
        if (tipoAhorroOptional.isPresent()){
            return ResponseEntity.ok(tipoAhorroOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<TipoAhorroDTO> registrarTipoAhorro(@RequestBody TipoAhorroDTO tipoAhorroDTO){
        tipoAhorroService.insertarTipoAhorro(tipoAhorroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoAhorroDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<TipoAhorroDTO> actualizarTipoAhorro(@RequestBody TipoAhorroDTO tipoAhorroDTO){
        try {
            tipoAhorroService.actualizarTipoAhorro(tipoAhorroDTO);
            return ResponseEntity.ok(tipoAhorroDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idTipoAhorro}")
    public ResponseEntity<TipoAhorroDTO> eliminarTipoAhorro(@PathVariable long idTipoAhorro){
        TipoAhorroDTO tipoAhorroDTO = new TipoAhorroDTO();
        tipoAhorroDTO.setIdTipoAhorro(idTipoAhorro);
        tipoAhorroService.eliminarTipoAhorro(tipoAhorroDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<TipoAhorroDTO> listTipoAhorroPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return tipoAhorroService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TipoAhorroDTO>> buscarTipoAhorroPorNombre(@RequestParam String nombreTipoAhorro){
        List<TipoAhorroDTO> tiposAhorros = tipoAhorroService.buscarTipoAhorroPorNombre(nombreTipoAhorro);
        return ResponseEntity.ok(tiposAhorros);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<TipoAhorroDTO>> listadoTiposAhorrosCompletos(){
        List<TipoAhorroDTO> tiposAhorros = tipoAhorroService.listadoTiposAhorrosCompletos();
        return ResponseEntity.ok(tiposAhorros);
    }

}
