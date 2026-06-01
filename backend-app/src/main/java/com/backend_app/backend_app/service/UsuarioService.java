package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Aporte;
import com.backend_app.backend_app.domain.Estado;
import com.backend_app.backend_app.domain.Usuario;
import com.backend_app.backend_app.dto.*;
import com.backend_app.backend_app.model.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<UsuarioDTO> listadoUsuario();

    Optional<UsuarioDTO> findUsuarioById(long identificacion);

    public UsuarioDTO convertirADTO(Usuario usuario);

    void registrarUsuario(UserRequest userRequest);

    void editarUsuario(UserRequest userRequest);

    void eliminarUsuario(UsuarioDTO usuarioDTO);

    Page<UsuarioDTO> findAll(Pageable pageable);

    Integer listarUsuarios();

    List<UsuarioDTO> buscarUsuariosPorNombre(String nombre);
    List<UsuarioDTO> ordenarPorFecha();

    List<EstadoDTO> usuariosPorEstado();

    List<UsuarioDTO> usuariosUltimoMes();

    List<UsuarioDTO> ordenarAlfabetico();

    List<UsuarioDTO> obtenerUsuariosUltimoMes();

    List<UsuarioDTO> ObtenerUsuariosCompletos();

    void refreshVistasMaterializadas();

    List<UsuarioDTO> obtenerUsuariosInactivos();

    List<AporteDTO> obtenerAportesUsuarioAdmin();

    List<AporteDTO> obtenerAportesUsuarioLogueado();

    List<AhorroUsuarioDTO> obtenerAhorrosUsuarioLogueado();

    List<TransaccionUsuarioDTO> obtenerTransaccioniesUsuarioLogueado();

    List<PrestamoUsuarioDTO> obtenerPrestamosUsuarioLogueado();

    List<InscripcionUsuarioDTO> obtenerInscripcionesActividadesUsuarioLogueado();

    List<AporteDTO> aportesUsuariosLogueado();

}
