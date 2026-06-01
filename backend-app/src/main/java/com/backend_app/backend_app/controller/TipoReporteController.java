package com.backend_app.backend_app.controller;


import com.backend_app.backend_app.domain.TipoReporte;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.TipoAhorroDTO;
import com.backend_app.backend_app.dto.TipoReporteDTO;
import com.backend_app.backend_app.service.TipoReporteService;
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
@RequestMapping("/api/tipo-reportes")
public class TipoReporteController {

    @Autowired
    private TipoReporteService tipoReporteService;

    @GetMapping
    public List<TipoReporteDTO> listadoTipoReportes(){
        return tipoReporteService.listadoTiposReportes();
    }

    @GetMapping("/{idTipoReporte}")
    public ResponseEntity<TipoReporteDTO> buscarTipoReportePorId(@PathVariable long idTipoReporte){
        Optional<TipoReporteDTO> tipoReporteOptional = tipoReporteService.buscarTipoReportePorId(idTipoReporte);
        if (tipoReporteOptional.isPresent()){
            return ResponseEntity.ok(tipoReporteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<TipoReporteDTO> registrarTipoReporte(@RequestBody TipoReporteDTO tipoReporteDTO){
        tipoReporteService.insertarTipoReporte(tipoReporteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoReporteDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<TipoReporteDTO> actualizarTipoReporte(@RequestBody TipoReporteDTO tipoReporteDTO){
        try {
            tipoReporteService.actualizarTipoReporte(tipoReporteDTO);
            return ResponseEntity.ok(tipoReporteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idTipoReporte}")
    public ResponseEntity<TipoAhorroDTO> eliminarTipoReporte(@PathVariable long idTipoReporte){
        TipoReporteDTO tipoReporteDTO = new TipoReporteDTO();
        tipoReporteDTO.setIdTipoReporte(idTipoReporte);
        tipoReporteService.eliminarTipoReporte(tipoReporteDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<TipoReporteDTO> listTipoReportePageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return tipoReporteService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TipoReporteDTO>> buscarTipoReportePorNombre(@RequestParam String nombreTipoReporte){
        List<TipoReporteDTO> tiposReportes = tipoReporteService.buscarTipoReporte(nombreTipoReporte);
        return ResponseEntity.ok(tiposReportes);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<TipoReporteDTO>> listadoTiposReportesCompletos(){
        List<TipoReporteDTO> tiposReportes = tipoReporteService.listadoTiposReportesCompletos();
        return ResponseEntity.ok(tiposReportes);
    }

}
