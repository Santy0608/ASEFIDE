package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@ToString
@Table(name = "FIDE_LUGAR_EVENTO_TB")
public class LugarEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LUGAR_EVENTO")
    private Long idLugarEvento;
    @Column(name = "NOMBRE_LUGAR")
    private String nombreLugar;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdLugarEvento(){
        return idLugarEvento;
    }

    public void setIdLugarEvento(Long idLugarEvento){
        this.idLugarEvento = idLugarEvento;
    }

    public String getNombreLugar(){
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar){
        this.nombreLugar = nombreLugar;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }
}
