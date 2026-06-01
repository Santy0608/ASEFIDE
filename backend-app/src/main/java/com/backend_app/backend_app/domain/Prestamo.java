package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FIDE_PRESTAMO_TB")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRESTAMO")
    private Long idPrestamo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    @Column(name = "MONTO_SOLICITADO")
    private BigDecimal montoSolicitado;
    @Column(name = "FECHA_APROBACION")
    private Date fechaAprobacion;
    @Column(name = "SALDO_PENDIENTE")
    private BigDecimal saldoPendiente;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
    @Column(name = "TASA_INTERESES")
    private Double tasaIntereses;
    @Column(name = "PLAZO_MESES")
    private Integer plazoMeses;

    public Long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTasaIntereses() {
        return tasaIntereses;
    }

    public void setTasaIntereses(Double tasaIntereses) {
        this.tasaIntereses = tasaIntereses;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
    }
}
