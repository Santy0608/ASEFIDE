package com.backend_app.backend_app.dto;

public class TipoReporteDTO {

    private Long idTipoReporte;
    private String nombre;

    private Long estadoId;
    private String nombreEstado;


    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Long getIdTipoReporte() {
        return idTipoReporte;
    }

    public void setIdTipoReporte(Long idTipoReporte) {
        this.idTipoReporte = idTipoReporte;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Long getEstadoId(Long estadoId){
        return estadoId;
    }

    public void setEstadoId(Long estadoId){
        this.estadoId = estadoId;
    }

}
