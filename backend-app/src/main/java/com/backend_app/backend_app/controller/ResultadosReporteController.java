package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.ResultadosReporte;
import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.ResultadosReporteDTO;
import com.backend_app.backend_app.service.ResultadosReporteService;
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
@RequestMapping("/api/resultados-reportes")
public class ResultadosReporteController {

    @Autowired
    private ResultadosReporteService resultadosReporteService;

    @GetMapping
    public List<ResultadosReporteDTO> listadoResultadosReportes(){
        return resultadosReporteService.listadoResultadosReporte();
    }

    @GetMapping("/{idResultado}")
    public ResponseEntity<ResultadosReporteDTO> buscarResultadoReportePorId(@PathVariable long idResultado){
        Optional<ResultadosReporteDTO> resultadoReporteOptional = resultadosReporteService.buscarResultadosReportePorId(idResultado);
        if (resultadoReporteOptional.isPresent()){
            return ResponseEntity.ok(resultadoReporteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ResultadosReporteDTO> guardarResultadosReporte(@RequestBody ResultadosReporteDTO resultadosReporteDTO){
        resultadosReporteService.insertarResultado(resultadosReporteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultadosReporteDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ResultadosReporteDTO> actualizarResultadoReporte(@RequestBody ResultadosReporteDTO resultadosReporteDTO){
        try {
            resultadosReporteService.actualizarResultado(resultadosReporteDTO);
            return ResponseEntity.ok(resultadosReporteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page/{page}")
    public Page<ResultadosReporteDTO> listResultadosReportePageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return resultadosReporteService.findAll(pageable);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<ResultadosReporteDTO>> listadoResultadosReportesCompletos(){
        List<ResultadosReporteDTO> resultadosReportes = resultadosReporteService.listadoResultadosReportesCompletos();
        return ResponseEntity.ok(resultadosReportes);
    }

}
