package com.backend_app.backend_app.controller;


import com.backend_app.backend_app.dto.ActividadDTO;
import com.backend_app.backend_app.dto.BeneficioDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.service.BeneficioService;
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
@RequestMapping("/api/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioService beneficioService;

    @GetMapping
    public List<BeneficioDTO> listadoBeneficios(){
        return beneficioService.listadoBeneficios();
    }

    @GetMapping("/beneficios-asociados")
    public ResponseEntity<List<BeneficioDTO>> obtenerBeneficiosAsociados(){
        List<BeneficioDTO> data = beneficioService.obtenerBeneficiosAsociados();
        return data.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(data);
    }

    @GetMapping("/{idBeneficio}")
    public ResponseEntity<BeneficioDTO> buscarBeneficioPorId(@PathVariable long idBeneficio){
        Optional<BeneficioDTO> beneficioOptional = beneficioService.buscarBeneficioPorId(idBeneficio);
        if (beneficioOptional.isPresent()){
            return ResponseEntity.ok(beneficioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<BeneficioDTO> registrarBeneficio(@RequestBody BeneficioDTO beneficioDTO){
        beneficioService.insertarBeneficio(beneficioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(beneficioDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<BeneficioDTO> actualizarBeneficio(@RequestBody BeneficioDTO beneficioDTO){
        try {
            beneficioService.actualizarBeneficio(beneficioDTO);
            return ResponseEntity.ok(beneficioDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idBeneficio}")
    public ResponseEntity<BeneficioDTO> eliminarBeneficio(@PathVariable long idBeneficio){
        BeneficioDTO beneficioDTO = new BeneficioDTO();
        beneficioDTO.setIdBeneficio(idBeneficio);
        beneficioService.eliminarBeneficio(beneficioDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<BeneficioDTO> listBeneficioPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return beneficioService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<BeneficioDTO>> buscarBeneficioPorNombre(@RequestParam String nombreBeneficio){
        List<BeneficioDTO> beneficios = beneficioService.buscarBeneficioPorNombre(nombreBeneficio);
        return ResponseEntity.ok(beneficios);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<BeneficioDTO>> listadoBeneficiosCompletos(){
        List<BeneficioDTO> beneficios = beneficioService.listadoBeneficiosCompletos();
        return ResponseEntity.ok(beneficios);
    }



}
