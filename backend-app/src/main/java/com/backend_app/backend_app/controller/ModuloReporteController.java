package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.ModuloReporteDTO;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.dto.TipoReporteDTO;
import com.backend_app.backend_app.service.ModuloReporteService;
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
@RequestMapping("/api/modulo-reporte")
public class ModuloReporteController {

    @Autowired
    private ModuloReporteService moduloReporteService;

    @GetMapping
    public List<ModuloReporteDTO> listadoModuloReportes(){
        return moduloReporteService.listadoModuloReportes();
    }

    @GetMapping("/{idModulo}")
    public ResponseEntity<ModuloReporteDTO> buscarModuloReportePorId(@PathVariable long idModulo){
        Optional<ModuloReporteDTO> moduloReporteOptional = moduloReporteService.buscarModuloReportePorId(idModulo);
        if (moduloReporteOptional.isPresent()){
            return ResponseEntity.ok(moduloReporteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<ModuloReporteDTO> registrarModuloReporte(@RequestBody ModuloReporteDTO moduloReporteDTO){
        moduloReporteService.guardarModuloReporte(moduloReporteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(moduloReporteDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ModuloReporteDTO> actualizarModuloReporte(@RequestBody ModuloReporteDTO moduloReporteDTO){
        try {
            moduloReporteService.actualizarModuloReporte(moduloReporteDTO);
            return ResponseEntity.ok(moduloReporteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idModulo}")
    public ResponseEntity<ModuloReporteDTO> eliminarModuloReporte(@PathVariable long idModulo){
        ModuloReporteDTO moduloReporteDTO = new ModuloReporteDTO();
        moduloReporteDTO.setIdModulo(idModulo);
        moduloReporteService.eliminarModuloReporte(moduloReporteDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<ModuloReporteDTO> listModuloReportePageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return moduloReporteService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ModuloReporteDTO>> buscarModuloReporte(@RequestParam String nombreModuloReporte){
        List<ModuloReporteDTO> modulosReportes = moduloReporteService.buscarModuloReportePorNombre(nombreModuloReporte);
        return ResponseEntity.ok(modulosReportes);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<ModuloReporteDTO>> listadoModulosReportesCompletos(){
        List<ModuloReporteDTO> modulosReportes = moduloReporteService.listadoModulosReportesCompletos();
        return ResponseEntity.ok(modulosReportes);
    }

}
