package com.backend_app.backend_app.dao;

import com.backend_app.backend_app.domain.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Long> {
}
