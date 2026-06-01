package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FIDE_DATOS_ASOCIADOS_TB")
public class DatosAsociados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DATOS_ASOCIADOS")
    private Long idDatosAsociados;

    @Column(name = "FECHA_AFILIACION")
    private Date fechaAfiliacion;

    @OneToMany(mappedBy = "datosAsociados", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aporte> aportes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PUESTO_EMPRESA", nullable = false)
    private PuestoEmpresa puestoEmpresa;

    public List<Aporte> getAportes(){
        return aportes;
    }

    public void setAportes(List<Aporte> aportes){
        this.aportes = aportes;
    }

    public Long getIdDatosAsociados(){
        return idDatosAsociados;
    }

    public void setIdDatosAsociados(Long idDatosAsociados){
        this.idDatosAsociados = idDatosAsociados;
    }

    public Date getFechaAfiliacion(){
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(Date fechaAfiliacion){
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public PuestoEmpresa getPuestoEmpresa(){
        return puestoEmpresa;
    }

    public void setPuestoEmpresa(PuestoEmpresa puestoEmpresa){
        this.puestoEmpresa = puestoEmpresa;
    }

}
