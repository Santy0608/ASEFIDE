package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.TipoTransaccion;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.dto.TipoTransaccionDTO;
import com.backend_app.backend_app.service.TipoTransaccionService;
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
@RequestMapping("/api/tipo-transacciones")
public class TipoTransaccionController {

    @Autowired
    private TipoTransaccionService tipoTransaccionService;

    @GetMapping
    public List<TipoTransaccionDTO> listadoTipoTransacciones(){
        return tipoTransaccionService.listadoTransacciones();
    }

    @GetMapping("/{idTipoTransaccion}")
    public ResponseEntity<TipoTransaccionDTO> buscarTipoTransaccionPorId(@PathVariable long idTipoTransaccion){
        Optional<TipoTransaccionDTO> tipoTransaccionOptional = tipoTransaccionService.buscarTipoTransaccionPorId(idTipoTransaccion);
        if (tipoTransaccionOptional.isPresent()){
            return ResponseEntity.ok(tipoTransaccionOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<TipoTransaccionDTO> registrarTipoTransaccion(@RequestBody TipoTransaccionDTO tipoTransaccionDTO){
        tipoTransaccionService.insertarTipoTransaccion(tipoTransaccionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoTransaccionDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<TipoTransaccionDTO> actualizarTipoTransaccion(@RequestBody TipoTransaccionDTO tipoTransaccionDTO){
        try {
            tipoTransaccionService.actualizarTipoTransaccion(tipoTransaccionDTO);
            return ResponseEntity.ok(tipoTransaccionDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idTipoTransaccion}")
    public ResponseEntity<TipoTransaccionDTO> eliminarTipoTransaccion(@PathVariable long idTipoTransaccion){
        TipoTransaccionDTO tipoTransaccionDTO = new TipoTransaccionDTO();
        tipoTransaccionDTO.setIdTipoTransaccion(idTipoTransaccion);
        tipoTransaccionService.eliminarTipoTransaccion(tipoTransaccionDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<TipoTransaccionDTO> listTipoTransaccionPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return tipoTransaccionService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TipoTransaccionDTO>> buscarTipoTransaccionPorNombre(@RequestParam String nombreTipoTransaccion){
        List<TipoTransaccionDTO> tiposTransacciones = tipoTransaccionService.buscarTipoTransaccionPorNombre(nombreTipoTransaccion);
        return ResponseEntity.ok(tiposTransacciones);
    }

    @GetMapping("/completas")
    public ResponseEntity<List<TipoTransaccionDTO>> listadoTiposTransaccionesCompletas(){
        List<TipoTransaccionDTO> tiposTransacciones = tipoTransaccionService.listadoTiposTransaccionesCompleatas();
        return ResponseEntity.ok(tiposTransacciones);
    }

}
