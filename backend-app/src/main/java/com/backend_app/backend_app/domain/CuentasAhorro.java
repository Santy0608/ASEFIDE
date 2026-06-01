package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.print.attribute.DateTimeSyntax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FIDE_CUENTAS_AHORRO_TB")
public class CuentasAhorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AHORRO")
    private Long idAhorro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    @Column(name = "MONTO_APORTE")
    private BigDecimal montoAporte;
    @Column(name = "FECHA_APERTURA")
    private Date fechaApertura;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_AHORRO", nullable = true)
    private TipoAhorro tipoAhorro;
    @Column(name = "SALDO_ACTUAL")
    private BigDecimal saldoActual;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdAhorro(){
        return idAhorro;
    }

    public void setIdAhorro(Long idAhorro){
        this.idAhorro = idAhorro;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public BigDecimal getMontoAporte(){
        return montoAporte;
    }

    public void setMontoAporte(BigDecimal montoAporte){
        this.montoAporte = montoAporte;
    }

    public Date getFechaApertura(){
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura){
        this.fechaApertura = fechaApertura;
    }

    public TipoAhorro getTipoAhorro(){
        return tipoAhorro;
    }

    public void setTipoAhorro(TipoAhorro tipoAhorro){
        this.tipoAhorro = tipoAhorro;
    }

    public BigDecimal getSaldoActual(){
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual){
        this.saldoActual = saldoActual;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

}
