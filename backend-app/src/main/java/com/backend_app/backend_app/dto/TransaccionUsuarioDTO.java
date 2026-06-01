package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TransaccionUsuarioDTO {

    private String nombreUsuario;
    private String tipoTransaccion;
    private BigDecimal monto;

    private Date fechaTransaccion;

    private String descripcion;

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipoTransaccion(){
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion){
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigDecimal getMonto(){
        return monto;
    }

    public void setMonto(BigDecimal monto){
        this.monto = monto;
    }

    public Date getFechaTransaccion(){
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion){
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

}
