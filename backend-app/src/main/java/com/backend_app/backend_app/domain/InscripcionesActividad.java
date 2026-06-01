package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FIDE_INSCRIPCIONES_ACTIVIDAD_TB")
public class InscripcionesActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long idInscripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACTIVIDAD", nullable = true)
    private Actividad actividad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    @Column(name = "FECHA_INSCRIPCION")
    private Date fechaInscripcion;
    @Column(name = "ASISTENCIA_CONFIRMADA")
    private boolean asistenciaConfirmada;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
