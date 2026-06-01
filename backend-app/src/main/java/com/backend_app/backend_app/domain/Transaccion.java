package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
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
@Table(name = "FIDE_TRANSACCION_TB")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACCION")
    private Long idTransaccion;
    @Column(name = "FECHA_TRANSACCION")
    private Date fechaTransaccion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_TRANSACCION")
    private TipoTransaccion tipoTransaccion;
    @Column(name = "MONTO_TOTAL")
    private BigDecimal montoTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
    @OneToOne(mappedBy = "transaccion")
    private MovimientosAhorro movimientosAhorro;
    @OneToOne(mappedBy = "transaccion")
    private PagosPrestamos pagosPrestamos;

    public Long getIdTransaccion(){
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion){
        this.idTransaccion = idTransaccion;
    }

    public Date getFechaTransaccion(){
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion){
        this.fechaTransaccion = fechaTransaccion;
    }

    public TipoTransaccion getTipoTransaccion(){
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion){
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigDecimal getMontoTotal(){
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal){
        this.montoTotal = montoTotal;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public  MovimientosAhorro getMovimientosAhorro(){
        return movimientosAhorro;
    }

    public void setMovimientosAhorro(MovimientosAhorro movimientosAhorro){
        this.movimientosAhorro = movimientosAhorro;
    }

    public PagosPrestamos getPagosPrestamos(){
        return pagosPrestamos;
    }

    public void setPagosPrestamos(PagosPrestamos pagosPrestamos){
        this.pagosPrestamos = pagosPrestamos;
    }

}
