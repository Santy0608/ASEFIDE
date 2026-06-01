package com.backend_app.backend_app.dao;

import com.backend_app.backend_app.domain.Rol;
import com.backend_app.backend_app.dto.RolDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombreRol(String nombreRol);

}
