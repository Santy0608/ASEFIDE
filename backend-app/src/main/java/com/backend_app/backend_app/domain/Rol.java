package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "FIDE_ROL_TB")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL")
    private Long idRol;
    @Column(name = "NOMBRE_ROL")
    private String nombreRol;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdRol(){
        return idRol;
    }

    public void setIdRol(Long idRol){
        this.idRol = idRol;
    }

    public String getNombreRol(){
        return nombreRol;
    }

    public void setNombreRol(String nombreRol){
        this.nombreRol = nombreRol;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

}
