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
@Table(name = "FIDE_CORREO_ELECTRONICO_TB")
public class Correo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CORREO")
    private Long idCorreo;
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    public Long getIdCorreo(){
        return idCorreo;
    }

    public void setIdCorreo(Long idCorreo){
        this.idCorreo = idCorreo;
    }

    public Long getIdentificacion(){
        return idCorreo;
    }

    public void setIdentificacion(Long idCorreo){
        this.idCorreo = idCorreo;
    }

    public String getCorreoElectronico(){
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico){
        this.correoElectronico = correoElectronico;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }
}
