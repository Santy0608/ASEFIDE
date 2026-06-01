package com.backend_app.backend_app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FIDE_SERVICIO_TB")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SERVICIO")
    private Long idServicio;
    @Column(name = "NOMBRE_SERVICIO")
    private String nombreServicio;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "VALOR_ESTIMADO")
    private BigDecimal valorEstimado;
    @Column(name = "STOCK")
    private Integer stock;
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
        this.imagenUrl = imagenUrl;;
    }

    public Long getIdServicio(){
        return idServicio;
    }

    public void setIdServicio(Long idServicio){
        this.idServicio = idServicio;
    }

    public String getNombreServicio(){
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio){
        this.nombreServicio = nombreServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public BigDecimal getValorEstimado(){
        return valorEstimado;
    }

    public void setValorEstimado(BigDecimal valorEstimado){
        this.valorEstimado = valorEstimado;
    }

    public Integer getStock(){
        return stock;
    }

    public void setStock(Integer stock){
        this.stock = stock;
    }

    public Categoria getCategory(){
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
