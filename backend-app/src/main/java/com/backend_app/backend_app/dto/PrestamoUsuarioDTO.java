package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class PrestamoUsuarioDTO {

    private String nombreUsuario;
    private BigDecimal montoSolicitado;
    private BigDecimal saldoPendiente;
    private Integer cuotasPagadas;
    private Date proximaFechaPago;
    private String estado;

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public BigDecimal getMontoSolicitado(){
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado){
        this.montoSolicitado = montoSolicitado;
    }

    public BigDecimal getSaldoPendiente(){
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente){
        this.saldoPendiente = saldoPendiente;
    }

    public Integer getCuotasPagadas(){
        return cuotasPagadas;
    }

    public void setCuotasPagadas(Integer cuotasPagadas){
        this.cuotasPagadas = cuotasPagadas;
    }

    public Date getProximaFechaPago(){
        return proximaFechaPago;
    }

    public void setProximaFechaPago(Date proximaFechaPago){
        this.proximaFechaPago = proximaFechaPago;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

}
