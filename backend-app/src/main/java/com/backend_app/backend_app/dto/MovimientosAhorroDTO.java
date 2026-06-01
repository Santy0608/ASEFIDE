package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class MovimientosAhorroDTO {

    private Long idMovimiento;
    private Long cuentasAhorroId;
    private BigDecimal montoAporte;

    private String nombreUsuario;

    private Date fechaApertura;

    private Long transaccionId;
    private BigDecimal montoTota;

    private BigDecimal monto;
    private Date fechaDeposito;

    private String tipoMovimiento;

    private String nombreTipo;

    private Date fechaTransaccion;

    public Date getFechaTransaccion(){
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion){
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipoMovimiento(){
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento){
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getMontoAporte() {
        return montoAporte;
    }

    public void setMontoAporte(BigDecimal montoAporte) {
        this.montoAporte = montoAporte;
    }

    public BigDecimal getMontoTota() {
        return montoTota;
    }

    public void setMontoTota(BigDecimal montoTota) {
        this.montoTota = montoTota;
    }

    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Long getCuentasAhorroId() {
        return cuentasAhorroId;
    }

    public void setCuentasAhorroId(Long cuentasAhorroId) {
        this.cuentasAhorroId = cuentasAhorroId;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
    }
}
