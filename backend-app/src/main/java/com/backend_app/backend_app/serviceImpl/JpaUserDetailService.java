package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.UsuarioRepository;
import com.backend_app.backend_app.domain.Usuario;
import com.backend_app.backend_app.dto.UsuarioDTO;
import com.backend_app.backend_app.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findUserByNombreUsuario(username);

        if (usuarioOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Usuario %s no encontrado", username));
        }



        Usuario usuario = usuarioOptional.get();
        if (usuario.getEstado().getIdEstado() == 2){
            throw new UsernameNotFoundException(String.format("Usuario %s no encontrado", username));
        }
        System.out.println("Usuario encontrado: " + usuario.getNombreUsuario());
        System.out.println("Hash en BD: " + usuario.getContrasenia());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("¿Match manual?: " + encoder.matches("12345", usuario.getContrasenia()));

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombreRol()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                usuario.getNombreUsuario(),
                usuario.getContrasenia(),
                true, true, true, true,
                authorities);
    }
}
