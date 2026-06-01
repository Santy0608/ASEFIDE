package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.RolRepository;
import com.backend_app.backend_app.dao.UsuarioRepository;
import com.backend_app.backend_app.domain.Rol;
import com.backend_app.backend_app.domain.Usuario;
import com.backend_app.backend_app.dto.*;
import com.backend_app.backend_app.functions.UsuarioFunctionRepository;
import com.backend_app.backend_app.model.UserRequest;
import com.backend_app.backend_app.repository.UsuarioStoredProcedureRepository;
import com.backend_app.backend_app.service.UsuarioService;
import com.backend_app.backend_app.views.InscripcionesActividadViewRepository;
import com.backend_app.backend_app.views.UsuarioViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioFunctionRepository usuarioFunctionRepository;

    @Autowired
    private UsuarioStoredProcedureRepository usuarioStoredProcedureRepository;

    @Autowired
    private UsuarioViewRepository usuarioViewRepository;

    @Autowired
    private InscripcionesActividadViewRepository inscripcionesActividadViewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder,
                              RolRepository rolRepository){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listadoUsuario() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> dtos = usuarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findUsuarioById(long identificacion) {
        return usuarioRepository.findById(identificacion).map(this::convertirADTO);
    }

    @Override
    public UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setIdentificacion(usuario.getIdentificacion());
        dto.setNombre(usuario.getNombre());
        dto.setApellidoPaterno(usuario.getApellidoPaterno());
        dto.setApellidoMaterno(usuario.getApellidoMaterno());
        if (usuario.getEstado() != null) {
            dto.setEstadoId(usuario.getEstado().getIdEstado());
            dto.setNombreEstado(usuario.getEstado().getNombre());
        }
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setContrasenia(usuario.getContrasenia());
        if (usuario.getDatosAsociados() != null && usuario.getDatosAsociados().getAportes() != null) {
            dto.setFechaAfiliacion(usuario.getDatosAsociados().getFechaAfiliacion());
            List<AporteUsuarioDTO> aportesDTO = usuario.getDatosAsociados().getAportes().stream()
                    .map(aporte -> {
                        AporteUsuarioDTO aporteDTO = new AporteUsuarioDTO();
                        aporteDTO.setIdAporte(aporte.getIdAporte());
                        aporteDTO.setMonto(aporte.getMonto());
                        aporteDTO.setFechaInicio(aporte.getFechaInicio());
                        aporteDTO.setFechaFinal(aporte.getFechaFinal());
                        return aporteDTO;
                    })
                    .collect(Collectors.toList());
            dto.setAportes(aportesDTO);
        }
        if (usuario.getDireccion() != null) {
            dto.setDireccionId(usuario.getDireccion().getIdDireccion());
            dto.setDistrito(usuario.getDireccion().getDistrito());
        }
        List<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombreRol)
                .collect(Collectors.toList());

        dto.setRoles(roles);
        dto.setAdmin(roles.contains("ROLE_ADMIN"));

        boolean esAsociado = roles.contains("ROLE_ASOCIADO");
        dto.setAsociado(esAsociado);

        if (usuario.getCorreos() != null && !usuario.getCorreos().isEmpty()) {
            List<String> listaCorreos = usuario.getCorreos().stream()
                    .map(c -> c.getCorreoElectronico())
                    .collect(Collectors.toList());
            dto.setCorreos(listaCorreos);

            //dto.setCorreoId(usuario.getCorreos().get(0).getIdCorreo());
        }

        if (usuario.getTelefonos() != null && !usuario.getTelefonos().isEmpty()) {
            List<String> listaTels = usuario.getTelefonos().stream()
                    .map(t -> t.getNumeroTelefono())
                    .collect(Collectors.toList());
            dto.setNumerosTelefono(listaTels);
        }

        return dto;
    }

    @Override
    @Transactional
    public void registrarUsuario(UserRequest userRequest) {

        userRequest.setContrasenia(passwordEncoder.encode(userRequest.getContrasenia()));

        Long usuarioId = usuarioStoredProcedureRepository.insertarUsuario(userRequest);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Error al recuperar el usuario creado"));

        usuario.setRoles(this.getRoles(userRequest));

        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void editarUsuario(UserRequest userRequest) {
        usuarioStoredProcedureRepository.editarUsuario(userRequest);

        Usuario usuario = usuarioRepository.findById(userRequest.getIdUsuario()).orElseThrow();

        usuario.setRoles(this.getRoles(userRequest));

        usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(UsuarioDTO usuarioDTO) {
        usuarioStoredProcedureRepository.eliminarUsuario(usuarioDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        return this.usuarioRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer listarUsuarios() {
        return usuarioFunctionRepository.listarUsuarios();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> buscarUsuariosPorNombre(String nombre) {
        return usuarioFunctionRepository.buscarPorNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> ordenarPorFecha() {
        return usuarioFunctionRepository.ordenarPorFecha();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDTO> usuariosPorEstado() {
        return usuarioFunctionRepository.usuariosPorEstado();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> usuariosUltimoMes() {
        return usuarioFunctionRepository.usuariosUltimoMes();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> ordenarAlfabetico() {
        return usuarioFunctionRepository.ordenarAlfabetico();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerUsuariosUltimoMes() {
        return usuarioViewRepository.getUsuariosUltimoMes();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> ObtenerUsuariosCompletos() {
        return usuarioViewRepository.getUsuariosCompletos();
    }

    @Override
    @Transactional
    public void refreshVistasMaterializadas() {
        usuarioViewRepository.refreshVistasMaterializadas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerUsuariosInactivos() {
        return usuarioViewRepository.getUsuariosInactivos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AporteDTO> obtenerAportesUsuarioAdmin() {
        return usuarioViewRepository.getAportesPorUsuarioAdmin();
    }


    @Override
    @Transactional(readOnly = true)
    public List<AporteDTO> obtenerAportesUsuarioLogueado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioViewRepository.getAportesPorUsuario(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AhorroUsuarioDTO> obtenerAhorrosUsuarioLogueado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioViewRepository.getAhorrosPorUsuario(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionUsuarioDTO> obtenerTransaccioniesUsuarioLogueado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioViewRepository.getTransaccionesPorUsuario(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoUsuarioDTO> obtenerPrestamosUsuarioLogueado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioViewRepository.getPrestamoUsuarioDTO(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionUsuarioDTO> obtenerInscripcionesActividadesUsuarioLogueado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioViewRepository.getInscripcionesActividadPorUsuario(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AporteDTO> aportesUsuariosLogueado() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return usuarioViewRepository.getAportesPorUsuarioLogueado(username);
    }


    private List<Rol> getRoles(UserRequest userRequest){
        List<Rol> roles = new ArrayList<>();
        Optional<Rol> optionalRoleAsociado = rolRepository.findByNombreRol("ROLE_ASOCIADO");
        optionalRoleAsociado.ifPresent(roles::add);
        if(userRequest.isAdmin()){
            Optional<Rol> optionalRoleAdmin = rolRepository.findByNombreRol("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }



}
