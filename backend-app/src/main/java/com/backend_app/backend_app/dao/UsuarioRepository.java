package com.backend_app.backend_app.dao;

import com.backend_app.backend_app.domain.Usuario;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUserByNombreUsuario(String nombreUsuario);

}
