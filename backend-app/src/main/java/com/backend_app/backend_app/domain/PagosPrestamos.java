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
@Table(name = "FIDE_PAGOS_PRESTAMOS_TB")
public class PagosPrestamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAGO")
    private long idPago;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRESTAMO")
    private Prestamo prestamo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID_TRANSACCION")
    private Transaccion transaccion;
    @Column(name = "MONTO_ABONADO")
    private Double montoAbonado;
    @Column(name = "FECHA_PAGO")
    private Date fechaPago;

    public Long getIdPago(){
        return idPago;
    }

    public void setIdPago(Long idPago){
        this.idPago = idPago;
    }

    public Prestamo getPrestamo(){
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo){
        this.prestamo = prestamo;
    }

    public Transaccion getTransaccion(){
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion){
        this.transaccion = transaccion;
    }

    public Double getMontoAbonado(){
        return montoAbonado;
    }

    public void setMontoAbonado(Double montoAbonado){
        this.montoAbonado = montoAbonado;
    }

    public Date getFechaPago(){
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago){
        this.fechaPago = fechaPago;
    }

}
