package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DatosAsociadosDTO {

    private Long idDatosAsociados;


    private Date fechaAfiliacion;

    private List<AporteUsuarioDTO> aportes;

    private Long puestoEmpresaId;
    private String nombrePuestoEmpresa;


    //DTO Listado Asociados con View
    private Long idUsuario;
    private String nombreCompleto;
    private String nombreUsuario;
    private Integer cantidadAportes;
    private BigDecimal aporteVigente;
    private BigDecimal totalAportes;
    private String puestoEmpresa;
    private String estadoUsuario;

    public Long getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario){
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto(){
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getCantidadAportes(){
        return cantidadAportes;
    }

    public void setCantidadAportes(Integer cantidadAportes){
        this.cantidadAportes = cantidadAportes;
    }

    public BigDecimal getTotalAportes(){
        return totalAportes;
    }

    public void setTotalAportes(BigDecimal totalAportes){
        this.totalAportes = totalAportes;
    }

    public String getPuestoEmpresa(){
        return puestoEmpresa;
    }

    public void setPuestoEmpresa(String puestoEmpresa){
        this.puestoEmpresa = puestoEmpresa;
    }

    public String getEstadoUsuario(){
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario){
        this.estadoUsuario = estadoUsuario;
    }

    public BigDecimal getAporteVigente(){
        return aporteVigente;
    }

    public void setAporteVigente(BigDecimal aporteVigente){
        this.aporteVigente = aporteVigente;
    }


    public List<AporteUsuarioDTO> getAportes(){
        return aportes;
    }

    public void setAportes(List<AporteUsuarioDTO> aportes){
        this.aportes = aportes;
    }

    public String getNombrePuestoEmpresa() {
        return nombrePuestoEmpresa;
    }

    public void setNombrePuestoEmpresa(String nombrePuestoEmpresa) {
        this.nombrePuestoEmpresa = nombrePuestoEmpresa;
    }

    public Long getIdDatosAsociados() {
        return idDatosAsociados;
    }

    public void setIdDatosAsociados(Long idDatosAsociados) {
        this.idDatosAsociados = idDatosAsociados;
    }


    public Date getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(Date fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public Long getPuestoEmpresaId() {
        return puestoEmpresaId;
    }

    public void setPuestoEmpresaId(Long puestoEmpresaId) {
        this.puestoEmpresaId = puestoEmpresaId;
    }
}
