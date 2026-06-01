package com.backend_app.backend_app.dto;


public class LugarEventoDTO {

    private Long idLugarEvento;
    private String nombreLugar;
    private Long estadoId;
    private String nombreEstado;

    public Long getIdLugarEvento() {
        return idLugarEvento;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public void setIdLugarEvento(Long idLugarEvento) {
        this.idLugarEvento = idLugarEvento;
    }

    public String getNombreLugar(){
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar){
        this.nombreLugar = nombreLugar;
    }

    public Long getEstadoId(){
        return estadoId;
    }

    public void setEstadoId(Long estadoId){
        this.estadoId = estadoId;
    }

}
