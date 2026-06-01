package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "FIDE_REPORTES_TB")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REPORTE")
    private Long idReporte;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_REPORTE", nullable = true)
    private TipoReporte tipoReporte;
    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;
    @Column(name = "FECHA_FINAL")
    private Date fechaFinal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MODULO", nullable = true)
    private ModuloReporte moduloReporte;
    @Column(name = "TOTAL_REGISTROS")
    private Integer totalRegistros;
    @Column(name = "RESUMEN_MONTOS")
    private Double resumenMontos;
    @Column(name = "FECHA_GENERACION")
    private Date fechaGeneracion;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;

    public Long getIdReporte(){
        return idReporte;
    }

    public void setIdReporte(Long idReporte){
        this.idReporte = idReporte;
    }

    public TipoReporte getTipoReporte(){
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporte tipoReporte){
        this.tipoReporte = tipoReporte;
    }

    public Date getFechaInicio(){
        return fechaInicio;
    }

    public Date getFechaFinal(){
        return fechaFinal;
    }

    public ModuloReporte getModuloReporte(){
        return moduloReporte;
    }

    public void setModuloReporte(ModuloReporte moduloReporte){
        this.moduloReporte = moduloReporte;
    }

    public Integer getTotalRegistros(){
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros){
        this.totalRegistros = totalRegistros;
    }

    public Double getResumenMontos(){
        return resumenMontos;
    }

    public void setResumenMontos(Double resumenMontos){
        this.resumenMontos = resumenMontos;
    }

    public Date getFechaGeneracion(){
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion){
        this.fechaGeneracion = fechaGeneracion;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

}
