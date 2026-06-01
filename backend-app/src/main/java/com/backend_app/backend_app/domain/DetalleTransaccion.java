package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FIDE_DETALLE_TRANSACCION_TB")
public class DetalleTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE")
    private Long idDetalle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TRANSACCION")
    private Transaccion transaccion;
    @Column(name = "CONCEPTO")
    private String concepto;
    @Column(name = "SUB_TOTAL")
    private BigDecimal subTotal;

    public Long getIdDetalle(){
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle){
        this.idDetalle = idDetalle;
    }

    public Transaccion getTransaccion(){
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion){
        this.transaccion = transaccion;
    }

    public String getConcepto(){
        return concepto;
    }

    public void setConcepto(String concepto){
        this.concepto = concepto;
    }

    public BigDecimal getSubTotal(){
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal){
        this.subTotal = subTotal;
    }


}
