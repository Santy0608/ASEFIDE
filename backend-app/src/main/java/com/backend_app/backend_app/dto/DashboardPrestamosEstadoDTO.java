package com.backend_app.backend_app.dto;

import java.math.BigDecimal;

public class DashboardPrestamosEstadoDTO {

    private String estado;
    private Integer totalPrestamos;
    private BigDecimal saldoPendienteTotal;
    private BigDecimal saldoPromedio;
    private BigDecimal montoSolicitadoTotal;
    private Double porcentajePrestamos;

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public Integer getTotalPrestamos(){
        return totalPrestamos;
    }

    public void  setTotalPrestamos(Integer totalPrestamos){
        this.totalPrestamos = totalPrestamos;
    }

    public BigDecimal getSaldoPendienteTotal(){
        return saldoPendienteTotal;
    }

    public void setSaldoPendienteTotal(BigDecimal saldoPendienteTotal){
        this.saldoPendienteTotal = saldoPendienteTotal;
    }

    public BigDecimal getSaldoPromedio(){
        return saldoPromedio;
    }

    public void setSaldoPromedio(BigDecimal saldoPromedio){
        this.saldoPromedio = saldoPromedio;
    }

    public BigDecimal getMontoSolicitadoTotal(){
        return montoSolicitadoTotal;
    }

    public void setMontoSolicitadoTotal(BigDecimal montoSolicitadoTotal){
        this.montoSolicitadoTotal = montoSolicitadoTotal;
    }

    public Double getPorcentajePrestamos(){
        return porcentajePrestamos;
    }

    public void setPorcentajePrestamos(Double porcentajePrestamos){
        this.porcentajePrestamos = porcentajePrestamos;
    }

}
