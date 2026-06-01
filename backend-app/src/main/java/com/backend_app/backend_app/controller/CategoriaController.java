package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.Categoria;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.CorreoDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import com.backend_app.backend_app.service.CategoriaService;
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
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> listadoCategorias() {
        return categoriaService.listadoCategorias();
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> bsucarCategoriaPorId(@PathVariable long idCategoria) {
        Optional<CategoriaDTO> categoriaOptional = categoriaService.buscarCategoriaPorId(idCategoria);
        if (categoriaOptional.isPresent()) {
            return ResponseEntity.ok(categoriaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<CategoriaDTO> registrarCategoria(@RequestBody CategoriaDTO categoriaDTO){
        categoriaService.registrarCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@RequestBody CategoriaDTO categoriaDTO){
        try {
            categoriaService.editarCategoria(categoriaDTO);
            return ResponseEntity.ok(categoriaDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> eliminarCategoria(@PathVariable long idCategoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(idCategoria);
        categoriaService.eliminarCategoria(categoriaDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<CategoriaDTO> listCategoriaPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 20);
        return categoriaService.findAll(pageable);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CategoriaDTO>> buscarCategoriaPorNombre(@RequestParam String nombreCategoria){
        List<CategoriaDTO> categorias = categoriaService.buscarCategoriaPorNombre(nombreCategoria);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/completas")
    public ResponseEntity<List<CategoriaDTO>> listadoCategoriasCompletas(){
        List<CategoriaDTO> categorias = categoriaService.listadoCategoriasCompletas();
        return ResponseEntity.ok(categorias);
    }

}
