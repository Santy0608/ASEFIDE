package com.backend_app.backend_app.dto;

public class BeneficioDTO {

    private Long idBeneficio;
    private String nombreBeneficio;
    private String descripcion;

    private Long categoriaId;
    private String nombreCategoria;

    private Long estadoId;
    private String nombreEstado;

    private String imagenUrl;

    public String getImagenUrl(){
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl){
        this.imagenUrl = imagenUrl;
    }

    public Long getIdBeneficio(){
        return idBeneficio;
    }

    public void setIdBeneficio(Long idBeneficio){
        this.idBeneficio = idBeneficio;
    }

    public String getNombreBeneficio(){
        return nombreBeneficio;
    }

    public void setNombreBeneficio(String nombreBeneficio){
        this.nombreBeneficio = nombreBeneficio;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Long getCategoriaId(){
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId){
        this.categoriaId = categoriaId;
    }

    public Long getEstadoId(){
        return estadoId;
    }

    public void setEstadoId(Long estadoId){
        this.estadoId = estadoId;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
