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
@Table(name = "FIDE_BENEFICIO_TB")
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BENEFICIO")
    private Long idBeneficio;
    @Column(name = "NOMBRE_BENEFICIO")
    private String nombreBeneficio;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA", nullable = true)
    private Categoria categoria;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    @Column(name = "IMAGEN_URL")
    private String imagenUrl;

    public String getImagenUrl(){
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl){
        this.imagenUrl = imagenUrl;
    }

    public Long getIdBeneficio(){
        return idBeneficio;
    }

    public void setIdBeneficio(Long idBeneficio){
        this.idBeneficio = idBeneficio;
    }

    public String getNombreBeneficio(){
        return nombreBeneficio;
    }

    public void setNombreBeneficio(String nombreBeneficio){
        this.nombreBeneficio = nombreBeneficio;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }


}
