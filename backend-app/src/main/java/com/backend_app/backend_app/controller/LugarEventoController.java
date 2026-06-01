package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.LugarEventoDTO;
import com.backend_app.backend_app.dto.RolDTO;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.service.LugarEventoService;
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
@RequestMapping("/api/lugar-evento")
public class LugarEventoController {

    @Autowired
    private LugarEventoService lugarEventoService;

    @GetMapping
    public List<LugarEventoDTO> listadoLugaresEventos(){
        return lugarEventoService.listadoLugaresEventos();
    }

    @GetMapping("/{idLugarEvento}")
    public ResponseEntity<LugarEventoDTO> buscarLugarEventoPorId(@PathVariable long idLugarEvento){
        Optional<LugarEventoDTO> lugarEventoOptional = lugarEventoService.buscarLugarEventoPorId(idLugarEvento);
        if (lugarEventoOptional.isPresent()){
            return ResponseEntity.ok(lugarEventoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<LugarEventoDTO> registrarLugarEvento(@RequestBody LugarEventoDTO lugarEventoDTO){
        lugarEventoService.registrarLugarEvento(lugarEventoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(lugarEventoDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<LugarEventoDTO> actualizarLugarEvento(@RequestBody LugarEventoDTO lugarEventoDTO){
        try {
            lugarEventoService.actualizarLugarEvento(lugarEventoDTO);
            return ResponseEntity.ok(lugarEventoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idLugarEvento}")
    public ResponseEntity<LugarEventoDTO> eliminarLugarEvento(@PathVariable long idLugarEvento){
        LugarEventoDTO lugarEventoDTO = new LugarEventoDTO();
        lugarEventoDTO.setIdLugarEvento(idLugarEvento);
        lugarEventoService.eliminarLugarEvento(lugarEventoDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<LugarEventoDTO> listLugarEventoPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return lugarEventoService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LugarEventoDTO>> buscarLugarEvento(@RequestParam String nombreLugarEvento){
        List<LugarEventoDTO> lugaresEventos = lugarEventoService.buscarLugarEvento(nombreLugarEvento);
        return ResponseEntity.ok(lugaresEventos);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<LugarEventoDTO>> listadoLugaresEventosCompletos(){
        List<LugarEventoDTO> lugaresEventos = lugarEventoService.listadoLugaresEventosCompletos();
        return ResponseEntity.ok(lugaresEventos);
    }

}
