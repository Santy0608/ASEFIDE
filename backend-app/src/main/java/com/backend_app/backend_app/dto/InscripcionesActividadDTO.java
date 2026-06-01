package com.backend_app.backend_app.dto;

import java.time.LocalDate;
import java.util.Date;

public class InscripcionesActividadDTO {

    private Long idInscripcion;

    private Long actividadId;

    private Long usuarioId;
    private String nombreActividad;

    private String identificacion;
    private String nombreUsuario;
    private String apellidoPaterno;


    private Date fechaInscripcion;
    private boolean asistenciaConfirmada;


    private Long estadoId;
    private String nombreEstado;

    public String getNombreEstado(){
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado){
        this.nombreEstado = nombreEstado;
    }

    public Long getEstadoId(){
        return estadoId;
    }

    public void setEstadoId(Long estadoId){
        this.estadoId = estadoId;
    }

    public Long getUsuarioId(){
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId){
        this.usuarioId = usuarioId;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Long getActividadId() {
        return actividadId;
    }

    public void setActividadId(Long actividadId) {
        this.actividadId = actividadId;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public boolean isAsistenciaConfirmada() {
        return asistenciaConfirmada;
    }

    public void setAsistenciaConfirmada(boolean asistenciaConfirmada) {
        this.asistenciaConfirmada = asistenciaConfirmada;
    }
}
