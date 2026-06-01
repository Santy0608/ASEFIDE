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
@Table(name = "FIDE_PUESTO_EMPRESA_ASOCIADO_TB")
public class PuestoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PUESTO_EMPRESA")
    private Long idPUestoEmpresa;
    @Column(name = "PUESTO_EMPRESA")
    private String puestoEmpresa;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdPuestoEmpresa(){
        return idPUestoEmpresa;
    }

    public void setIdPuestoEmpresa(Long idPUestoEmpresa){
        this.idPUestoEmpresa = idPUestoEmpresa;
    }

    public String getPuestoEmpresa(){
        return puestoEmpresa;
    }

    public void setPuestoEmpresa(String puestoEmpresa){
        this.puestoEmpresa = puestoEmpresa;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }



}
