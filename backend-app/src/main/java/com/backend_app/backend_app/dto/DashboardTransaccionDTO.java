package com.backend_app.backend_app.dto;

import java.math.BigDecimal;

public class DashboardTransaccionDTO {

    private String mes;
    private String tipo;
    private Integer cantidad;
    private BigDecimal montoTotal;

    public String getMes(){
        return mes;
    }

    public void setMes(String mes){
        this.mes = mes;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public Integer getCantidad(){
        return cantidad;
    }

    public void setCantidad(Integer cantidad){
        this.cantidad = cantidad;
    }

    public BigDecimal getMontoTotal(){
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal){
        this.montoTotal = montoTotal;
    }



}
