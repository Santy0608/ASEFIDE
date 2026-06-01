package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.Actividad;
import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.LugarEventoDTO;
import com.backend_app.backend_app.service.ActividadService;
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
@RequestMapping("/api/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping
    public List<ActividadDTO> listadoActividades(){
        return actividadService.listadoActivides();
    }

    @GetMapping("/actividades-asociados")
    public ResponseEntity<List<ActividadDTO>> obtenerActividadesAsociados(){
        List<ActividadDTO> data = actividadService.obtenerActividadesAsociados();
        return data.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(data);
    }

    @GetMapping("/{idActividad}")
    public ResponseEntity<ActividadDTO> buscarActividadPorId(@PathVariable long idActividad){
        Optional<ActividadDTO> actividadDTOOptional = actividadService.buscarActividadPorId(idActividad);
        if (actividadDTOOptional.isPresent()){
            return ResponseEntity.ok(actividadDTOOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<ActividadDTO> registrarActividad(@RequestBody ActividadDTO actividadDTO){
        actividadService.insertarActividad(actividadDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(actividadDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ActividadDTO> actualizarActividad(@RequestBody ActividadDTO actividadDTO){
        try {
            actividadService.actualizarActividad(actividadDTO);
            return ResponseEntity.ok(actividadDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idActividad}")
    public ResponseEntity<ActividadDTO> eliminarActividad(@PathVariable long idActividad){
        ActividadDTO actividadDTO = new ActividadDTO();
        actividadDTO.setIdActividad(idActividad);
        actividadService.elimminarActividad(actividadDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<ActividadDTO> listActividadPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return actividadService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ActividadDTO>> buscarActividadPorNombre(@RequestParam String nombre){
        List<ActividadDTO> actividades = actividadService.buscarActividadPorNombre(nombre);
        return ResponseEntity.ok(actividades);
    }

    @GetMapping("/actividades-programadas")
    public ResponseEntity<List<ActividadDTO>> actividadesProgramadas(){
        List<ActividadDTO> actividades = actividadService.listadoActividadesProgramadas();
        return ResponseEntity.ok(actividades);
    }

    @GetMapping("/completas")
    public ResponseEntity<List<ActividadDTO>> listadoActividadesCompletas(){
        List<ActividadDTO> actividades = actividadService.listadoActividadesCompletas();
        return ResponseEntity.ok(actividades);
    }

}
