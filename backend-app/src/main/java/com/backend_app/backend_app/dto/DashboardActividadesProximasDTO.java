package com.backend_app.backend_app.dto;

public class DashboardActividadesProximasDTO {

    private String nombreActividad;
    private String lugar;
    private Integer totalInscritos;
    private Integer cupoDisponible;
    private Double porcentajeOcupacion;
    private Integer diasParaEvento;

    public String getNombreActividad(){
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad){
        this.nombreActividad = nombreActividad;
    }

    public String getLugar(){
        return lugar;
    }

    public void setLugar(String lugar){
        this.lugar = lugar;
    }

    public Integer getTotalInscritos(){
        return totalInscritos;
    }

    public void setTotalInscritos(Integer totalInscritos){
        this.totalInscritos = totalInscritos;
    }

    public Integer getCupoDisponible(){
        return cupoDisponible;
    }

    public void setCupoDisponible(Integer cupoDisponible){
        this.cupoDisponible = cupoDisponible;
    }

    public Double getPorcentajeOcupacion(){
        return porcentajeOcupacion;
    }

    public void setPorcentajeOcupacion(Double porcentajeOcupacion){
        this.porcentajeOcupacion = porcentajeOcupacion;
    }

    public Integer getDiasParaEvento(){
        return diasParaEvento;
    }

    public void setDiasParaEvento(Integer diasParaEvento){
        this.diasParaEvento = diasParaEvento;
    }

}
