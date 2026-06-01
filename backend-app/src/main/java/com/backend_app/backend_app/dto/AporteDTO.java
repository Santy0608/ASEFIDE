package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AporteDTO {

    private Long idUsuario;
    private String nombreCompleto;
    private String nombreUsuario;
    private BigDecimal aporteMensual;
    private Date fechaAfiliacion;
    private String estadoUsuario;

    private Integer cantidadAportes;

    private BigDecimal totalAportes;

    private BigDecimal aporteVigente;

    private String puestoEmpresa;




    private Long idAporte;
    private BigDecimal monto;
    private Date fechaInicio;
    private Date fechaFin;

    public Long getIdAporte(){
        return idAporte;
    }

    public void setIdAporte(Long idAporte){
        this.idAporte = idAporte;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin(){
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin){
        this.fechaFin = fechaFin;
    }

    public String getPuestoEmpresa(){
        return puestoEmpresa;
    }

    public void setPuestoEmpresa(String puestoEmpresa){
        this.puestoEmpresa = puestoEmpresa;
    }


    public BigDecimal getTotalAportes(){
        return totalAportes;
    }

    public void setTotalAportes(BigDecimal totalAportes){
        this.totalAportes = totalAportes;
    }

    public BigDecimal getAporteVigente(){
        return aporteVigente;
    }

    public void setAporteVigente(BigDecimal aporteVigente){
        this.aporteVigente = aporteVigente;
    }

    public Integer getCantidadAportes(){
        return cantidadAportes;
    }

    public void setCantidadAportes(Integer cantidadAportes){
        this.cantidadAportes = cantidadAportes;
    }



    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public BigDecimal getAporteMensual() {
        return aporteMensual;
    }

    public void setAporteMensual(BigDecimal aporteMensual) {
        this.aporteMensual = aporteMensual;
    }

    public Date getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(Date fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }
}
