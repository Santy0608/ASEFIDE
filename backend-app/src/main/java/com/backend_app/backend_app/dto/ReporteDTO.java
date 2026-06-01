package com.backend_app.backend_app.dto;

import java.time.LocalDate;
import java.util.Date;

public class ReporteDTO {

    private Long idReporte;

    private Long tipoReporteId;
    private String nombreTipoReporte;


    private Date fechaInicio;

    private Date fechaFinal;

    private Long idModuloReporte;
    private String nombreModuloReporte;

    private Long estadoId;
    private String nombreEstado;


    private String usuarioId;
    private String nombreUsuario;
    private String apellidoPaterno;

    private Integer totalRegistros;
    private Double resumenMontos;
    private Date fechaGeneracion;

    public Long getIdReporte() {
        return idReporte;
    }

    public String getNombreTipoReporte() {
        return nombreTipoReporte;
    }

    public void setNombreTipoReporte(String nombreTipoReporte) {
        this.nombreTipoReporte = nombreTipoReporte;
    }

    public String getNombreModuloReporte() {
        return nombreModuloReporte;
    }

    public void setNombreModuloReporte(String nombreModuloReporte) {
        this.nombreModuloReporte = nombreModuloReporte;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
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

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
    }

    public Long getTipoReporteId() {
        return tipoReporteId;
    }

    public void setTipoReporteId(Long tipoReporteId) {
        this.tipoReporteId = tipoReporteId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Long getIdModuloReporte() {
        return idModuloReporte;
    }

    public void setIdModuloReporte(Long idModuloReporte) {
        this.idModuloReporte = idModuloReporte;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Double getResumenMontos() {
        return resumenMontos;
    }

    public void setResumenMontos(Double resumenMontos) {
        this.resumenMontos = resumenMontos;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}
