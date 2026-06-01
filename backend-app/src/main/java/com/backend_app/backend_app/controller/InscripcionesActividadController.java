package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.InscripcionesActividad;
import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.InscripcionesActividadDTO;
import com.backend_app.backend_app.service.InscripcionesActividadService;
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
@RequestMapping("/api/inscripciones-actividades")
public class InscripcionesActividadController {

    @Autowired
    private InscripcionesActividadService inscripcionesActividadService;

    @GetMapping
    public List<InscripcionesActividadDTO> listadoInscripcionesActividades(){
        return inscripcionesActividadService.listadoInscripcionesActividad();
    }

    @GetMapping("/{idInscripcion}")
    public ResponseEntity<InscripcionesActividadDTO> buscarInscripcionesPorActividad(@PathVariable  long idInscripcion){
        Optional<InscripcionesActividadDTO> inscripcionesActividadOptional = inscripcionesActividadService.buscarInscripcionActividadPorId(idInscripcion);
        if (inscripcionesActividadOptional.isPresent()){
            return ResponseEntity.ok(inscripcionesActividadOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/guardar")
    public ResponseEntity<InscripcionesActividadDTO> registrrInscripcion(@RequestBody InscripcionesActividadDTO inscripcionesActividadDTO){
        inscripcionesActividadService.agregarInscripcionActividad(inscripcionesActividadDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionesActividadDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<InscripcionesActividadDTO> actualizarInscripcion(@RequestBody InscripcionesActividadDTO inscripcionesActividadDTO){
        try {
            inscripcionesActividadService.actualizarInscripcionActividad(inscripcionesActividadDTO);
            return ResponseEntity.ok(inscripcionesActividadDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idInscripcion}")
    public ResponseEntity<ActividadDTO> eliminarInscripcion(@PathVariable long idInscripcion){
        InscripcionesActividadDTO inscripcionesActividadDTO = new InscripcionesActividadDTO();
        inscripcionesActividadDTO.setIdInscripcion(idInscripcion);
        inscripcionesActividadService.eliminarInscripcionActividad(inscripcionesActividadDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<InscripcionesActividadDTO> listInscripcionesActividadPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 25);
        return inscripcionesActividadService.findAll(pageable);
    }

    @GetMapping("/completas")
    public ResponseEntity<List<InscripcionesActividadDTO>> listadoInscripcionesActividadesCompletas(){
        List<InscripcionesActividadDTO> inscripcionesActividades = inscripcionesActividadService.listadoInscripcionesActividadesCompletas();
        return ResponseEntity.ok(inscripcionesActividades);
    }

}
