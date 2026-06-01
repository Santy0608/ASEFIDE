package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class PagosPrestamosDTO {

    private Long idPago;

    private Long prestamoId;
    private BigDecimal montoSolicitado;
    private Date fechaAprobacion;
    private BigDecimal saldoPendiente;


    private Long transaccionId;
    private BigDecimal montoTotal;
    private Date fechaTransaccion;


    private Double montoAbonado;

    private Date fechaPago;


    private String nombreUsuario;

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public Date getFechaTransaccion(){
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion){
        this.fechaTransaccion = fechaTransaccion;
    }

    public Long getIdPago() {
        return idPago;
    }

    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Double getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(Double montoAbonado) {
        this.montoAbonado = montoAbonado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
}
