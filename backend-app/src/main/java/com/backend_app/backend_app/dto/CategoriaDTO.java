package com.backend_app.backend_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoriaDTO {

    @JsonProperty("idCategoria")
    private Long idCategoria;
    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("estadoId")
    private Long estadoId;
    private String nombreEstado;

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Long getEstadoId(){
        return estadoId;
    }

    public void setEstadoId(Long estadoId){
        this.estadoId = estadoId;
    }

    public Long getIdCategoria(){
        return idCategoria;
    }

}
