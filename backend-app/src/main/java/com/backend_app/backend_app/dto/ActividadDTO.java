package com.backend_app.backend_app.dto;

import java.time.LocalDate;
import java.util.Date;

public class ActividadDTO {

    private Long idActividad;
    private String nombre;
    private String descripcion;
    private Date fechaEvento;

    private Long  lugarEventoId;
    private String nombreLugarEvento;

    private Integer cupoTotal;

    private Long estadoId;
    private String nombreEstado;

    private Long usuarioId;
    private String nombreUsuario;
    private String apellidoPaterno;

    private String estadoActividad;

    private String imagenUrl;

    public String getImagenUrl(){
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl){
        this.imagenUrl = imagenUrl;
    }

    public String getEstadoActividad(){
        return estadoActividad;
    }

    public void setEstadoActividad(String estadoActividad){
        this.estadoActividad = estadoActividad;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNombreLugarEvento() {
        return nombreLugarEvento;
    }

    public void setNombreLugarEvento(String nombreLugarEvento) {
        this.nombreLugarEvento = nombreLugarEvento;
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

    public Long getIdActividad(){
        return idActividad;
    }

    public void setIdActividad(Long idActividad){
        this.idActividad = idActividad;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public  String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Date getFechaEvento(){
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento){
        this.fechaEvento = fechaEvento;
    }

    public Long getLugarEventoId(){
        return lugarEventoId;
    }

    public void setLugarEventoId(Long lugarEventoId){
        this.lugarEventoId = lugarEventoId;
    }

    public Integer getCupoTotal(){
        return cupoTotal;
    }

    public void setCupoTotal(Integer cupoTotal){
        this.cupoTotal = cupoTotal;
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

}
