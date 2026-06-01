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
@Table(name = "FIDE_TIPO_AHORRO_TB")
public class TipoAhorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_AHORRO")
    private Long idTipoAhorro;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdTipoAhorro(){
        return idTipoAhorro;
    }

    public void setIdTipoAhorro(Long idTipoAhorro){
        this.idTipoAhorro = idTipoAhorro;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

}
