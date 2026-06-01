package com.backend_app.backend_app.dto;

public class PuestoEmpresaDTO {

    private Long idPuestoEmpresa;
    private String puestoEmpresa;
    private Long estadoId;
    private String nombreEstado;

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Long getIdPuestoEmpresa() {
        return idPuestoEmpresa;
    }

    public void setIdPuestoEmpresa(Long idPuestoEmpresa) {
        this.idPuestoEmpresa = idPuestoEmpresa;
    }

    public String getPuestoEmpresa(){
        return puestoEmpresa;
    }

    public void setPuestoEmpresa(String puestoEmpresa){
        this.puestoEmpresa = puestoEmpresa;
    }

    public Long getEstadoId(){
        return estadoId;
    }

    public void setEstadoId(Long estadoId){
        this.estadoId = estadoId;
    }

}
