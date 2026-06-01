package com.backend_app.backend_app.controller;

import com.backend_app.backend_app.domain.InscripcionesActividad;
import com.backend_app.backend_app.domain.Usuario;
import com.backend_app.backend_app.dto.*;
import com.backend_app.backend_app.model.UserRequest;
import com.backend_app.backend_app.service.PdfService;
import com.backend_app.backend_app.service.ReporteService;
import com.backend_app.backend_app.service.UsuarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PdfService pdfService;

    @GetMapping
    public List<UsuarioDTO> listadoUsuarios(){
        return usuarioService.listadoUsuario();
    }

    @GetMapping("/completos")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosCompletos(){
        List<UsuarioDTO> data = usuarioService.ObtenerUsuariosCompletos();
        return data.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(data);
    }

    @GetMapping("/ultimo-mes-usuarios")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosUltimoMes(){
        List<UsuarioDTO> data = usuarioService.obtenerUsuariosUltimoMes();
        return data.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(data);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosInactivos(){
        List<UsuarioDTO> data = usuarioService.obtenerUsuariosInactivos();
        return data.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(data);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshVistas(){
        usuarioService.refreshVistasMaterializadas();
        return ResponseEntity.ok("Vistas materializadas actualizadas correctamente");
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<UsuarioDTO> findUsuarioById(@PathVariable long identificacion){
        Optional<UsuarioDTO> usuarioOptional = usuarioService.findUsuarioById(identificacion);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<UserRequest> registrarUsuario(@RequestBody UserRequest userRequest){
        usuarioService.registrarUsuario(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarUsuario(@RequestBody UserRequest userRequest){
        try {
            usuarioService.editarUsuario(userRequest);
            return ResponseEntity.ok("Usuario actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> eliminarUsuario(@PathVariable long idUsuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(idUsuario);
        usuarioService.eliminarUsuario(usuarioDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{page}")
    public Page<UsuarioDTO> listUsuarioPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return usuarioService.findAll(pageable);
    }

    @GetMapping("/listar")
    public ResponseEntity<Integer> listarUsuarios() {
        Integer resultado = usuarioService.listarUsuarios();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<UsuarioDTO> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/por-fecha")
    public ResponseEntity<List<UsuarioDTO>> ordenarPorFecha() {
        List<UsuarioDTO> usuarios = usuarioService.ordenarPorFecha();

        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/por-estado")
    public ResponseEntity<List<EstadoDTO>> usuariosPorEstado() {
        List<EstadoDTO> estados = usuarioService.usuariosPorEstado();

        if (estados == null || estados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/ultimo-mes")
    public ResponseEntity<List<UsuarioDTO>> usuariosUltimoMes() {
        List<UsuarioDTO> usuarios = usuarioService.usuariosUltimoMes();

        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/alfabetico")
    public ResponseEntity<List<UsuarioDTO>> ordenarAlfabetico() {
        List<UsuarioDTO> usuarios = usuarioService.ordenarAlfabetico();
        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/mis-aportes")
    public ResponseEntity<List<AporteDTO>> obtenerMisAportes() {
        return ResponseEntity.ok(usuarioService.aportesUsuariosLogueado());
    }

    @GetMapping("/mis-aportes/descargar")
    public ResponseEntity<byte[]> downloadPdf() {
        List<AporteDTO> aportes = usuarioService.obtenerAportesUsuarioLogueado();
        byte[] pdfBytes = pdfService.generarReporteAportePdf(aportes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "mis_aportes.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/mis-ahorros")
    public ResponseEntity<List<AhorroUsuarioDTO>> obtenerMisAhorros(){
        List<AhorroUsuarioDTO> usuarios = usuarioService.obtenerAhorrosUsuarioLogueado();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/mis-transacciones")
    public ResponseEntity<List<TransaccionUsuarioDTO>> obtenerMisTransacciones(){
        List<TransaccionUsuarioDTO> usuarios = usuarioService.obtenerTransaccioniesUsuarioLogueado();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/mis-prestamos")
    public ResponseEntity<List<PrestamoUsuarioDTO>> obtenerMisPrestamos(){
        List<PrestamoUsuarioDTO> usuarios = usuarioService.obtenerPrestamosUsuarioLogueado();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/mis-inscripciones-actividades")
    public ResponseEntity<List<InscripcionUsuarioDTO>> obtenerMisInscripcionesActividades(){
        List<InscripcionUsuarioDTO> usuarios = usuarioService.obtenerInscripcionesActividadesUsuarioLogueado();
        return ResponseEntity.ok(usuarios);
    }



}
