package com.backend_app.backend_app.dao;

import com.backend_app.backend_app.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
