package com.backend_app.backend_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CorreoDTO {

    @JsonProperty("idCorreo")
    private Long idCorreo;
    @JsonProperty("correoElectronico")
    private String correoElectronico;
    @JsonProperty("estadoId")
    private Long estadoId;
    private String nombreEstado;

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }


    public void setCorreoElectronico(String correoElectronico){
        this.correoElectronico = correoElectronico;
    }

    public Long getEstadoId(){
        return estadoId;
    }

    public void setEstadoId(Long estadoId){
        this.estadoId = estadoId;
    }

    public Long getIdCorreo(){
        return idCorreo;
    }

    public void setIdCorreo(Long idCorreo){
        this.idCorreo = idCorreo;
    }

}
