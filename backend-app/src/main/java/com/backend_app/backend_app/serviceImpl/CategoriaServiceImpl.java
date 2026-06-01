package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dao.CategoriaRepository;
import com.backend_app.backend_app.domain.Categoria;
import com.backend_app.backend_app.dto.CategoriaDTO;
import com.backend_app.backend_app.functions.CategoriaFunctionRepository;
import com.backend_app.backend_app.repository.CategoriaStoredProcedureRepository;
import com.backend_app.backend_app.service.CategoriaService;
import com.backend_app.backend_app.views.CategoriaViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaFunctionRepository categoriaFunctionRepository;

    @Autowired
    private CategoriaStoredProcedureRepository categoriaStoredProcedureRepository;

    @Autowired
    private CategoriaViewRepository categoriaViewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> listadoCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> dtos = categorias.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> buscarCategoriaPorId(long idCategoria) {
        return categoriaRepository.findById(idCategoria).map(this::convertirADTO);
    }

    @Override
    public CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        if (categoria.getEstado() != null) {
            dto.setEstadoId(categoria.getEstado().getIdEstado());
            dto.setNombreEstado(categoria.getEstado().getNombre());
        }
        return dto;
    }

    @Override
    @Transactional
    public void registrarCategoria(CategoriaDTO categoriaDTO) {
        categoriaStoredProcedureRepository.insertarCategoria(categoriaDTO);
    }

    @Override
    @Transactional
    public void editarCategoria(CategoriaDTO categoriaDTO) {
        categoriaStoredProcedureRepository.editarCategoria(categoriaDTO);
    }

    @Override
    @Transactional
    public void eliminarCategoria(CategoriaDTO categoriaDTO) {
        categoriaStoredProcedureRepository.eliminarCategoria(categoriaDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable).map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> buscarCategoriaPorNombre(String nombreCategoria) {
        return categoriaFunctionRepository.buscarCategoria(nombreCategoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> listadoCategoriasCompletas() {
        return categoriaViewRepository.getCategoriasCompletas();
    }


}
