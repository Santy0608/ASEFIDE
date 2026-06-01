package com.backend_app.backend_app.dto;


public class RolDTO {

    private Long idRol;
    private String nombreRol;
    private Long estadoId;
    private String nombreEstado;

    public String getNombreEstado(){
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado){
        this.nombreEstado = nombreEstado;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol(){
        return nombreRol;
    }

    public void setNombreRol(String nombreRol){
        this.nombreRol = nombreRol;
    }

    public Long getEstadoId(){
        return estadoId;
    }

    public void setEstadoId(Long estadoId){
        this.estadoId = estadoId;
    }

}
