package com.backend_app.backend_app.dto;

import java.math.BigDecimal;

public class DashboardKpiDTO {

    private Integer totalAsociadosActivos;
    private BigDecimal totalAhorros;
    private Integer totalPrestamosPendientes;
    private Integer transaccionesMesActual;

    public Integer getTotalAsociadosActivos(){
        return totalAsociadosActivos;
    }

    public void setTotalAsociadosActivos(Integer totalAsociadosActivos){
        this.totalAsociadosActivos = totalAsociadosActivos;
    }

    public BigDecimal getTotalAhorros(){
        return totalAhorros;
    }

    public void setTotalAhorros(BigDecimal totalAhorros){
        this.totalAhorros = totalAhorros;
    }

    public Integer getTotalPrestamosPendientes(){
        return totalPrestamosPendientes;
    }

    public void setTotalPrestamosPendientes(Integer totalPrestamosPendientes){
        this.totalPrestamosPendientes = totalPrestamosPendientes;
    }

    public Integer getTransaccionesMesActual(){
        return transaccionesMesActual;
    }

    public void setTransaccionesMesActual(Integer transaccionesMesActual){
        this.transaccionesMesActual = transaccionesMesActual;
    }



}
