package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class TransaccionDTO {

    private Long idTransaccion;

    private Date fechaTransaccion;

    private Long tipoTransaccionId;
    private String nombreTipoTransaccion;


    private BigDecimal montoTotal;

    private Long usuarioId;
    private String identificacion;
    private String nombreUsuario;
    private String apellidoPaterno;

    private Long estadoId;
    private String nombreEstado;

    private Long movimientosAhorroId;
    private BigDecimal montoAhorros;

    private Integer totalTransacciones;

    private Long pagosPrestamosId;
    private Double montoAbonadoPagoPrestamo;

    public Integer getTotalTransacciones(){
        return totalTransacciones;
    }

    public void setTotalTransacciones(Integer totalTransacciones){
        this.totalTransacciones = totalTransacciones;
    }

    public String getIdentificacion(){
        return identificacion;
    }

    public void setIdentificacion(String identificacion){
        this.identificacion = identificacion;
    }

    public String getNombreTipoTransaccion() {
        return nombreTipoTransaccion;
    }

    public void setNombreTipoTransaccion(String nombreTipoTransaccion) {
        this.nombreTipoTransaccion = nombreTipoTransaccion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public BigDecimal getMontoAhorros() {
        return montoAhorros;
    }

    public void setMontoAhorros(BigDecimal montoAhorros) {
        this.montoAhorros = montoAhorros;
    }

    public Double getMontoAbonadoPagoPrestamo() {
        return montoAbonadoPagoPrestamo;
    }

    public void setMontoAbonadoPagoPrestamo(Double montoAbonadoPagoPrestamo) {
        this.montoAbonadoPagoPrestamo = montoAbonadoPagoPrestamo;
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public Long getTipoTransaccionId() {
        return tipoTransaccionId;
    }

    public void setTipoTransaccionId(Long tipoTransaccionId) {
        this.tipoTransaccionId = tipoTransaccionId;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public Long getMovimientosAhorroId() {
        return movimientosAhorroId;
    }

    public void setMovimientosAhorroId(Long movimientosAhorroId) {
        this.movimientosAhorroId = movimientosAhorroId;
    }

    public Long getPagosPrestamosId() {
        return pagosPrestamosId;
    }

    public void setPagosPrestamosId(Long pagosPrestamosId) {
        this.pagosPrestamosId = pagosPrestamosId;
    }
}
