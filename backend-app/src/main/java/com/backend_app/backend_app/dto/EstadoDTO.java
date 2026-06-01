package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.util.Date;

public class EstadoDTO {

    private Long idEstado;
    private String nombre;

    private Integer prestamoId;

    private Integer cantidadUsuarios;

    private BigDecimal montoSolicitado;

    private BigDecimal saldoPendiente;

    private Double tasaIntereses;

    private Integer plazoMeses;

    private Date fechaAprobacion;

    private String estadoPrestamo;

    private String cliente;

    private Integer cantidadPrestamos;

    public Integer getCantidadPrestamos(){
        return cantidadPrestamos;
    }

    public void setCantidadPrestamos(Integer cantidadPrestamos){
        this.cantidadPrestamos = cantidadPrestamos;
    }


    public String getCliente(){
        return cliente;
    }

    public void setCliente(String cliente){
        this.cliente = cliente;
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

    public Integer getPrestamoId(){
        return prestamoId;
    }

    public void setPrestamoId(Integer prestamoId){
        this.prestamoId = prestamoId;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente){
        this.saldoPendiente = saldoPendiente;
    }

    public Double getTasaIntereses(){
        return tasaIntereses;
    }

    public void setTasaIntereses(Double tasaIntereses){
        this.tasaIntereses = tasaIntereses;
    }

    public Integer getPlazoMeses(){
        return plazoMeses;
    }

    public void setPlazoMeses(Integer plazoMeses){
        this.plazoMeses = plazoMeses;
    }

    public Date getFechaAprobacion(){
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion){
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getEstadoPrestamo(){
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(String estadoPrestamo){
        this.estadoPrestamo = estadoPrestamo;
    }

    public void setCantidadUsuarios(Integer cantidadUsuarios){
        this.cantidadUsuarios = cantidadUsuarios;
    }

    public Integer getCantidadUsuarios(){
        return cantidadUsuarios;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

}
