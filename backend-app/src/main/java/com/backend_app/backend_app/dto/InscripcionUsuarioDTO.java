package com.backend_app.backend_app.dto;

public class InscripcionUsuarioDTO {

    private String nombreActividad;
    private String fechaEvento;
    private String nombreLugar;
    private String estadoInscripcion;

    public String getNombreActividad(){
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad){
        this.nombreActividad = nombreActividad;
    }

    public String getFechaEvento(){
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento){
        this.fechaEvento = fechaEvento;
    }

    public String getNombreLugar(){
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar){
        this.nombreLugar = nombreLugar;
    }

    public String getEstadoInscripcion(){
        return estadoInscripcion;
    }

    public void setEstadoInscripcion(String estadoInscripcion){
        this.estadoInscripcion = estadoInscripcion;
    }

}
