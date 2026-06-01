package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FIDE_MOVIMIENTOS_AHORRO_TB")
public class MovimientosAhorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMIENTO")
    private Long idMovimiento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AHORRO")
    private CuentasAhorro cuentasAhorro;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID_TRANSACCION")
    private Transaccion transaccion;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "FECHA_DEPOSITO")
    private Date fechaDeposito;

    public Long getIdMovimiento(){
        return idMovimiento;
    }

    public void setIdMovimiento(Long idMovimiento){
        this.idMovimiento = idMovimiento;
    }

    public CuentasAhorro getCuentasAhorro(){
        return cuentasAhorro;
    }

    public void setCuentasAhorro(CuentasAhorro cuentasAhorro){
        this.cuentasAhorro = cuentasAhorro;
    }

    public Transaccion getTransaccion(){
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion){
        this.transaccion = transaccion;
    }

    public BigDecimal getMonto(){
        return monto;
    }

    public void setMonto(BigDecimal monto){
        this.monto = monto;
    }

    public Date getFechaDeposito(){
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito){
        this.fechaDeposito = fechaDeposito;
    }

}
