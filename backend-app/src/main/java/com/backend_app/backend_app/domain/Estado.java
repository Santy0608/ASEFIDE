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
@Table(name = "FIDE_ESTADO_TB")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO")
    private Long idEstado;
    @Column(name = "NOMBRE")
    private String nombre;

    public Long getIdEstado(){
        return idEstado;
    }

    public void setIdEstado(Long idEstado){
        this.idEstado = idEstado;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }


}

