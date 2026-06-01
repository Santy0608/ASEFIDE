package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.Rol;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.RolDTO;
import com.backend_app.backend_app.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<RolDTO> listadoRoles(){
        return rolService.listadoRoles();
    }

    @GetMapping("/{idRol}")
    public ResponseEntity<RolDTO> buscandoRolPorId(@PathVariable long idRol){
        Optional<RolDTO> rolOptional = rolService.buscarRolPorId(idRol);
        if (rolOptional.isPresent()){
            return ResponseEntity.ok(rolOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<RolDTO> registrarRol(@RequestBody RolDTO rolDTO){
        rolService.insertarRol(rolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<RolDTO> actualizarRol(@RequestBody RolDTO rolDTO){
        try {
            rolService.actualizarRol(rolDTO);
            return ResponseEntity.ok(rolDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idRol}")
    public ResponseEntity<RolDTO> eliminarRol(@PathVariable long idRol){
        RolDTO rolDTO = new RolDTO();
        rolDTO.setIdRol(idRol);
        rolService.eliminarRol(rolDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/completos")
    public ResponseEntity<List<RolDTO>> listadoRolesCompletos(){
        List<RolDTO> roles = rolService.listadoRolesCompletos();
        return ResponseEntity.ok(roles);
    }


}
