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
@Table(name = "FIDE_NUMERO_TELEFONO_TB")
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NUMERO")
    private Long idTelefono;
    @Column(name = "NUMERO_TELEFONO")
    private String numeroTelefono;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdTelefono(){
        return idTelefono;
    }

    public void setIdentificacion(Long idTelefono){
        this.idTelefono = idTelefono;
    }

    public String getNumeroTelefono(){
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono){
        this.numeroTelefono = numeroTelefono;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }



}
