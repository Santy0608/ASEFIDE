package com.backend_app.backend_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UsuarioDTO {

    private Long idUsuario;
    private String identificacion;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    private Long telefonoId;
    private String numeroTelefono;

    @JsonProperty("correoId")
    private Long correoId;
    private String correo;

    private Long estadoId;
    private String nombreEstado;

    private String nombreUsuario;
    private String contrasenia;

    @JsonProperty("direccionId")
    private Long direccionId;
    private String distrito;

    private Date fechaAfiliacion;

    private String estadoUsuario;

    private boolean admin;

    private boolean asociado;

    private List<String> roles;

    public List<String> getRoles(){
        return roles;
    }

    private Long rolId;

    private List<Long> correosIds;

    private List<String> correos;
    private List<String> numerosTelefono;

    private List<AporteUsuarioDTO> aportes;

    public List<String> getCorreos() {
        return correos;
    }

    public void setCorreos(List<String> correos) {
        this.correos = correos;
    }

    public List<AporteUsuarioDTO> getAportes(){
        return aportes;
    }

    public void setAportes(List<AporteUsuarioDTO> aportes){
        this.aportes = aportes;
    }

    public List<String> getNumerosTelefono() {
        return numerosTelefono;
    }

    public void setNumerosTelefono(List<String> numerosTelefono) {
        this.numerosTelefono = numerosTelefono;
    }

    public List<Long> getCorreosIds() {
        return correosIds;
    }

    public void setCorreosIds(List<Long> correosIds) {
        this.correosIds = correosIds;
    }

    public void setRoles(List<String> roles){
        this.roles = roles;
    }

    public String getEstadoUsuario(){
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario){
        this.estadoUsuario = estadoUsuario;
    }

    private Integer totalTransacciones;

    public Integer getTotalTransacciones(){
        return totalTransacciones;
    }

    public void setTotalTransacciones(Integer totalTransacciones){
        this.totalTransacciones = totalTransacciones;;
    }

    private BigDecimal saldoActual;

    public BigDecimal getSaldoActual(){
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual){
        this.saldoActual = saldoActual;
    }


    public Date getFechaAfiliacion(){
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(Date fechaAfiliacion){
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public String getDistrito(){
        return distrito;
    }

    public void setDistrito(String distrito){
        this.distrito = distrito;
    }

    @JsonProperty("identificacionDatosAsociados")
    private Long identificacionDatosAsociados;
    private BigDecimal aporteMensual;

    public Long getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(Long direccionId) {
        this.direccionId = direccionId;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public BigDecimal getAporteMensual() {
        return aporteMensual;
    }

    public void setAporteMensual(BigDecimal aporteMensual) {
        this.aporteMensual = aporteMensual;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Long getTelefonoId() {
        return telefonoId;
    }

    public void setTelefonoId(Long telefonoId) {
        this.telefonoId = telefonoId;
    }

    public Long getCorreoId() {
        return correoId;
    }

    public void setCorreoId(Long correoId) {
        this.correoId = correoId;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Long getIdentificacionDatosAsociados() {
        return identificacionDatosAsociados;
    }

    public void setIdentificacionDatosAsociados(Long identificacionDatosAsociados) {
        this.identificacionDatosAsociados = identificacionDatosAsociados;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public boolean isAsociado(){
        return asociado;
    }

    public void setAsociado(boolean asociado){
        this.asociado = asociado;
    }

    public Long getRolId(){
        return rolId;
    }

    public void setRolId(Long rolId){
        this.rolId = rolId;
    }
}
