package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.DatosAsociados;
import com.backend_app.backend_app.dto.AporteDTO;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.DatosAsociadosDTO;
import com.backend_app.backend_app.service.DatosAsociadosService;
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
@RequestMapping("/api/datos-asociados")
public class DatosAsociadosController {

    @Autowired
    private DatosAsociadosService datosAsociadosService;

    @GetMapping
    public List<DatosAsociadosDTO> listadoDatosAsociadosDTO(){
        return datosAsociadosService.listadoDatosAsociados();
    }

    @GetMapping("/{idDatosAsociados}")
    public ResponseEntity<DatosAsociadosDTO> buscarDatosAsociadosPorId(@PathVariable long idDatosAsociados){
        Optional<DatosAsociadosDTO> datosAsociadosOptional = datosAsociadosService.buscarDatosAsociadosPorId(idDatosAsociados);
        if (datosAsociadosOptional.isPresent()){
            return ResponseEntity.ok(datosAsociadosOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<DatosAsociadosDTO> registrarDatosAsociadosDTO(@RequestBody DatosAsociadosDTO datosAsociadosDTO){
        datosAsociadosService.inseertarDatosAsociados(datosAsociadosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(datosAsociadosDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<DatosAsociadosDTO> actualizarDatosAsociados(@RequestBody DatosAsociadosDTO datosAsociadosDTO){
        try {
            datosAsociadosService.actualizarDatosAsociados(datosAsociadosDTO);
            return ResponseEntity.ok(datosAsociadosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page/{page}")
    public Page<DatosAsociadosDTO> listDatosAsociadosPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return datosAsociadosService.findAll(pageable);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<DatosAsociadosDTO>> listadoDatosAsociados(){
        List<DatosAsociadosDTO> datosAsociados = datosAsociadosService.listadoDatosAsociadosCompletos();
        return ResponseEntity.ok(datosAsociados);
    }

}
