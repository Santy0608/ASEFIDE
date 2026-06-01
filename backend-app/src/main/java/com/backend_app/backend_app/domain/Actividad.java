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
@Table(name = "FIDE_ACTIVIDAD_TB")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACTIVIDAD")
    private Long idActividad;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_EVENTO")
    private Date fechaEvento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LUGAR_EVENTO", nullable = true)
    private LugarEvento lugarEvento;
    @Column(name = "CUPO_TOTAL")
    private Integer cupoTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    @Column(name = "IMAGEN_URL")
    private String imagenUrl;

    public String getImagenUrl(){
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl){
        this.imagenUrl = imagenUrl;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public LugarEvento getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(LugarEvento lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public Integer getCupoTotal() {
        return cupoTotal;
    }

    public void setCupoTotal(Integer cupoTotal) {
        this.cupoTotal = cupoTotal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }



}
