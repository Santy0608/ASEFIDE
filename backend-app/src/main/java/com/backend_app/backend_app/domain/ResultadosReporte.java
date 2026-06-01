package com.backend_app.backend_app.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FIDE_RESULTADOS_REPORTE_TB")
public class ResultadosReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESULTADO")
    private Long idResultado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REPORTE")
    private Reporte reporte;
    @Column(name = "METRICA_NOMBRE")
    private String metricaNombre;
    @Column(name = "METRICA_VALOR")
    private String metricaValor;

    public Long getIdResultado(){
        return idResultado;
    }

    public void setIdResultado(Long idResultado){
        this.idResultado = idResultado;
    }

    public Reporte getReporte(){
        return reporte;
    }

    public void setReporte(Reporte reporte){
        this.reporte = reporte;
    }

    public String getMetricaNombre(){
        return metricaNombre;
    }

    public void setMetricaNombre(String metricaNombre){
        this.metricaNombre = metricaNombre;
    }

    public String getMetricaValor(){
        return metricaValor;
    }

    public void setMetricaValor(String metricaValor){
        this.metricaValor = metricaValor;
    }

}
