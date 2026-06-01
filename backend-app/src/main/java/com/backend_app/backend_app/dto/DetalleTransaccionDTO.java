package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.util.Date;

public class DetalleTransaccionDTO {

    private Long idDetalle;

    private Long transaccionId;
    private BigDecimal montoTotal;

    private Date fechaTransaccion;

    private String concepto;
    private BigDecimal subTotal;

    public Date getFechaTransaccion(){
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion){
        this.fechaTransaccion = fechaTransaccion;
    }

    public Long getIdDetalle() {
        return idDetalle;
    }

    public BigDecimal getMontoTotalTransaccion() {
        return montoTotal;
    }

    public void setMontoTotalTransaccion(BigDecimal montoTotalTransaccion) {
        this.montoTotal = montoTotalTransaccion;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}
