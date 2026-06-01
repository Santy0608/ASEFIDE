package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FIDE_APORTE_TB")
public class Aporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_APORTE")
    private Long idAporte;

    @Column(name = "MONTO")
    private BigDecimal monto;

    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;

    @Column(name = "FECHA_FIN")
    private Date fechaFinal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DATOS_ASOCIADOS", nullable = false)
    private DatosAsociados datosAsociados;

    public Long getIdAporte(){
        return idAporte;
    }

    public void setIdAporte(Long idAporte){
        this.idAporte = idAporte;
    }

    public BigDecimal getMonto(){
        return monto;
    }

    public void setMonto(BigDecimal monto){
        this.monto = monto;
    }

    public Date getFechaInicio(){
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal(){
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal){
        this.fechaFinal = fechaFinal;
    }

    public DatosAsociados getDatosAsociados() {
        return datosAsociados;
    }

    public void setDatosAsociados(DatosAsociados datosAsociados) {
        this.datosAsociados = datosAsociados;
    }
}
