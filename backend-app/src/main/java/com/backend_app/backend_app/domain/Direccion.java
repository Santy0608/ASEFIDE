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
@Table(name = "FIDE_DIRECCION_TB")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIRECCION")
    private Long idDireccion;
    @Column(name = "PROVINCIA")
    private String provincia;
    @Column(name = "CANTON")
    private String canton;
    @Column(name = "DISTRITO")
    private String distrito;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdDireccion(){
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion){
        this.idDireccion = idDireccion;
    }

    public String getProvincia(){
        return provincia;
    }

    public void setProvincia(String provincia){
        this.provincia = provincia;
    }

    public String getCanton(){
        return canton;
    }

    public void setCnaton(String canton){
        this.canton = canton;
    }

    public String getDistrito(){
        return distrito;
    }

    public void settDistrito(String distrito){
        this.distrito = distrito;
    }

    public Estado getEstado(){
        return estado;
    }

    public void seEstado(Estado estado){
        this.estado = estado;
    }

}
