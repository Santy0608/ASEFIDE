package com.backend_app.backend_app.dao;

import com.backend_app.backend_app.domain.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {



}
