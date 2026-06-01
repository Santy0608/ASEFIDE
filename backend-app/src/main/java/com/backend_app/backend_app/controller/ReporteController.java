package com.backend_app.backend_app.controller;


import com.backend_app.backend_app.domain.Reporte;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.ModuloReporteDTO;
import com.backend_app.backend_app.dto.ReporteDTO;
import com.backend_app.backend_app.repository.ReporteStoredProcedureRepository;
import com.backend_app.backend_app.service.PdfService;
import com.backend_app.backend_app.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteStoredProcedureRepository reporteStoredProcedureRepository;

    @Autowired
    private PdfService pdfService;

    @GetMapping
    public List<ReporteDTO> listadoReportes(){
        return reporteService.listadoReportes();
    }

    @GetMapping("/{idReporte}")
    public ResponseEntity<ReporteDTO> buscarReportePorId(@PathVariable long idReporte){
        Optional<ReporteDTO> reporteOptional = reporteService.buscarReportePorId(idReporte);
        if (reporteOptional.isPresent()){
            return ResponseEntity.ok(reporteOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<ReporteDTO> registrarReporte(@RequestBody ReporteDTO reporteDTO){
        reporteService.insertarReporte(reporteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ReporteDTO> actualizarReporte(@RequestBody ReporteDTO reporteDTO){
        try {
            reporteService.actualizarReporte(reporteDTO);
            return ResponseEntity.ok(reporteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idReporte}")
    public ResponseEntity<ReporteDTO> eliminarReporte(@PathVariable long idReporte){
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setIdReporte(idReporte);
        reporteService.eliminarReporte(reporteDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<ReporteDTO> listCReportePageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return reporteService.findAll(pageable);
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> descargarReporte(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFinal) {

        Date inicio = Date.valueOf(fechaInicio); // formato "yyyy-MM-dd"
        Date fin = Date.valueOf(fechaFinal);

        List<ReporteDTO> reportes = reporteStoredProcedureRepository.generarReporte(inicio, fin);
        byte[] pdf = pdfService.generarReportePdf(reportes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<ReporteDTO>> listadoReportesCompletos(){
        List<ReporteDTO> reportes = reporteService.listadoReportesCompletos();
        return ResponseEntity.ok(reportes);
    }

}
