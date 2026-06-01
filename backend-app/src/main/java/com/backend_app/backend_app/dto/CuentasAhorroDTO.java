package com.backend_app.backend_app.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class CuentasAhorroDTO {

    private Long idAhorro;

    private Long usuarioId;
    private String identificacion;
    private String nombreUsuario;
    private String apellidoPaterno;

    private BigDecimal montoAporte;
    private Date fechaApertura;

    private Long tipoAhorroId;
    private String nombreTipoAhorro;

    private BigDecimal saldoActual;

    private Long estadoId;
    private String nombreEstado;

    private BigDecimal totalAhorro;

    private String estadoCuenta;

    public String getIdentificacion(){
        return identificacion;
    }

    public void setIdentificacion(String identificacion){
        this.identificacion = identificacion;
    }

    public String getEstadoCuenta(){
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta){
        this.estadoCuenta = estadoCuenta;
    }

    public BigDecimal getTotalAhorro(){
        return totalAhorro;
    }

    public void setTotalAhorro(BigDecimal totalAhorro){
        this.totalAhorro = totalAhorro;
    }

    public String getApellidoPaterno(){
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno){
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreTipoAhorro() {
        return nombreTipoAhorro;
    }

    public void setNombreTipoAhorro(String nombreTipoAhorro) {
        this.nombreTipoAhorro = nombreTipoAhorro;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Long getIdAhorro() {
        return idAhorro;
    }

    public void setIdAhorro(Long idAhorro) {
        this.idAhorro = idAhorro;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getMontoAporte() {
        return montoAporte;
    }

    public void setMontoAporte(BigDecimal montoAporte) {
        this.montoAporte = montoAporte;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Long getTipoAhorroId() {
        return tipoAhorroId;
    }

    public void setTipoAhorroId(Long tipoAhorroId) {
        this.tipoAhorroId = tipoAhorroId;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }
}
