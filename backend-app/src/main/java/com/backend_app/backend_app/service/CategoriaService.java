package com.backend_app.backend_app.service;

import com.backend_app.backend_app.domain.Categoria;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<CategoriaDTO> listadoCategorias();

    Optional<CategoriaDTO> buscarCategoriaPorId(long idCategoria);

    CategoriaDTO convertirADTO(Categoria categoria);

    void registrarCategoria(CategoriaDTO categoriaDTO);

    void editarCategoria(CategoriaDTO categoriaDTO);

    void eliminarCategoria(CategoriaDTO categoriaDTO);

    Page<CategoriaDTO> findAll(Pageable pageable);

    List<CategoriaDTO> buscarCategoriaPorNombre(String nombreCategoria);

    List<CategoriaDTO> listadoCategoriasCompletas();

}
