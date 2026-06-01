package com.backend_app.backend_app.controller;


import com.backend_app.backend_app.domain.PuestoEmpresa;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.PuestoEmpresaDTO;
import com.backend_app.backend_app.service.PuestoEmpresaService;
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
@RequestMapping("/api/puestos-empresas")
public class PuestoEmpresaController {

    @Autowired
    private PuestoEmpresaService puestoEmpresaService;

    @GetMapping
    public List<PuestoEmpresaDTO> puestoEmpresas(){
        return puestoEmpresaService.listadoPuestosEmpresas();
    }

    @GetMapping("/{idPuestoEmpresa}")
    public ResponseEntity<PuestoEmpresaDTO> buscarPuestoEmpresaPorId(@PathVariable long idPuestoEmpresa){
        Optional<PuestoEmpresaDTO> puestoEmpresaOptional = puestoEmpresaService.buscarPuestoEmpresaPorId(idPuestoEmpresa);
        if (puestoEmpresaOptional.isPresent()){
            return ResponseEntity.ok(puestoEmpresaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<PuestoEmpresaDTO> registrarPuestoEmpresa(@RequestBody PuestoEmpresaDTO puestoEmpresa){
        puestoEmpresaService.insertarPuestoEmpresa(puestoEmpresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(puestoEmpresa);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<PuestoEmpresaDTO> actualizarPuestoEmpresa(@RequestBody PuestoEmpresaDTO puestoEmpresaDTO){
        try {
            puestoEmpresaService.actualizarPuestoEmpresa(puestoEmpresaDTO);
            return ResponseEntity.ok(puestoEmpresaDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idPuestoEmpresa}")
    public ResponseEntity<PuestoEmpresaDTO> eliminarPuestoEmpresa(@PathVariable long idPuestoEmpresa){
        PuestoEmpresaDTO puestoEmpresaDTO = new PuestoEmpresaDTO();
        puestoEmpresaDTO.setIdPuestoEmpresa(idPuestoEmpresa);
        puestoEmpresaService.eliminarPuestoEmpresa(puestoEmpresaDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<PuestoEmpresaDTO> listPuestoEmpresaPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return puestoEmpresaService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PuestoEmpresaDTO>> buscarPuestoEmpresa(@RequestParam String puestoEmpresa){
        List<PuestoEmpresaDTO> puestosEmpresas = puestoEmpresaService.buscarPuestoEmpresa(puestoEmpresa);
        return ResponseEntity.ok(puestosEmpresas);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<PuestoEmpresaDTO>> listadoPuestosEmpresasCompletos(){
        List<PuestoEmpresaDTO> puestosEmpresas = puestoEmpresaService.listadoPuestosEmpresasCompletos();
        return ResponseEntity.ok(puestosEmpresas);
    }

}
