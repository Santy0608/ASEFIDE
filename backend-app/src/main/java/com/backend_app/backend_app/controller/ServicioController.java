package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.Servicio;
import com.backend_app.backend_app.dto.BeneficioDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.ServicioDTO;
import com.backend_app.backend_app.service.ServicioService;
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
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<ServicioDTO> listadoServicios(){
        return servicioService.listadoServicios();
    }

    @GetMapping("/servicios-asociados")
    public ResponseEntity<List<ServicioDTO>> obtenerServiciosAsociados(){
        List<ServicioDTO> data = servicioService.obtenerServiciosAsociados();
        return data.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(data);
    }

    @GetMapping("/{idServicio}")
    public ResponseEntity<ServicioDTO> buscarServicioPorId(@PathVariable long idServicio){
        Optional<ServicioDTO> servicioOptional = servicioService.buscarServicioPorId(idServicio);
        if (servicioOptional.isPresent()) {
            return ResponseEntity.ok(servicioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<ServicioDTO> registrarServicio(@RequestBody ServicioDTO servicio){
        servicioService.insertarServicio(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ServicioDTO> actualizarServicio(@RequestBody ServicioDTO servicio){
        try {
            servicioService.editarServicio(servicio);
            return ResponseEntity.ok(servicio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idServicio}")
    public ResponseEntity<ServicioDTO> eliminarServicio(@PathVariable long idServicio){
        ServicioDTO servicioDTO = new ServicioDTO();
        servicioDTO.setIdServicio(idServicio);
        servicioService.eliminarServicio(servicioDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<ServicioDTO> listServicioPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return servicioService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ServicioDTO>> buscarServicioPorNombre(@RequestParam String nombreServicio){
        List<ServicioDTO> servicios = servicioService.buscarServicioPorNombre(nombreServicio);
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<ServicioDTO>> listadoServiciosCompletos(){
        List<ServicioDTO> servicios = servicioService.listadoServiciosCompletos();
        return ResponseEntity.ok(servicios);
    }

}
