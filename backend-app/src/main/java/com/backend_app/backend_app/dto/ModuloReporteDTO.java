package com.backend_app.backend_app.dto;

public class ModuloReporteDTO {

    private Long idModulo;
    private String nombre;
    private String descripcion;
    private Long estadoId;
    private String nombreEstado;

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
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

}
