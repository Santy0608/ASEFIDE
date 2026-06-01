package com.backend_app.backend_app.dto;


import java.math.BigDecimal;

public class DashboardTipoAhorroDTO {

    private String tipoAhorro;
    private Integer totalCuentas;
    private BigDecimal saldoTotal;
    private BigDecimal saldoPromedio;
    private Double porcentajeCuentas;

    public String getTipoAhorro(){
        return tipoAhorro;
    }

    public void setTipoAhorro(String tipoAhorro){
        this.tipoAhorro = tipoAhorro;
    }

    public Integer getTotalCuentas(){
        return totalCuentas;
    }

    public void setTotalCuentas(Integer totalCuentas){
        this.totalCuentas = totalCuentas;
    }

    public BigDecimal getSaldoTotal(){
        return saldoTotal;
    }

    public void setSaldoTotal(BigDecimal saldoTotal){
        this.saldoTotal = saldoTotal;
    }

    public BigDecimal getSaldoPromedio(){
        return saldoPromedio;
    }

    public void setSaldoPromedio(BigDecimal saldoPromedio){
        this.saldoPromedio = saldoPromedio;
    }

    public Double getPorcentajeCuentas(){
        return porcentajeCuentas;
    }

    public void setPorcentajeCuentas(Double porcentajeCuentas){
        this.porcentajeCuentas = porcentajeCuentas;
    }



}
