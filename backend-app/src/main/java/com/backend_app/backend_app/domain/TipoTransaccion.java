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
@Table(name = "FIDE_TIPO_TRANSACCION_TB")
public class TipoTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_TRANSACCION")
    private Long idTipoTransaccion;
    private String nombre;
    private String descripcion;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdTipoTransaccion(){
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(Long idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
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
