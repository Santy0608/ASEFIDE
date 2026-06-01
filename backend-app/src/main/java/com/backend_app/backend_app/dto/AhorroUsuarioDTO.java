package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class AhorroUsuarioDTO {

    private Long idUsuario;
    private String nombreCompleto;
    private String nombreUsuario;
    private Long idAhorro;
    private Date fechaApertura;
    private BigDecimal montoAporte;
    private BigDecimal saldoActual;
    private String tipoAhorro;
    private String estadoCuenta;

    public Long getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario){
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto(){
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public Long getIdAhorro(){
        return idAhorro;
    }

    public void setIdAhorro(Long idAhorro){
        this.idAhorro = idAhorro;
    }

    public Date getFechaApertura(){
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura){
        this.fechaApertura = fechaApertura;
    }

    public BigDecimal getMontoAporte(){
        return montoAporte;
    }

    public void setMontoAporte(BigDecimal montoAporte){
        this.montoAporte = montoAporte;
    }

    public BigDecimal getSaldoActual(){
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual){
        this.saldoActual = saldoActual;
    }

    public String getTipoAhorro(){
        return tipoAhorro;
    }

    public void setTipoAhorro(String tipoAhorro){
        this.tipoAhorro = tipoAhorro;
    }

    public String getEstadoCuenta(){
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta){
        this.estadoCuenta = estadoCuenta;
    }

}
