package com.backend_app.backend_app.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AporteUsuarioDTO {

    private Long idAporte;

    private BigDecimal monto;

    private Date fechaInicio;

    private Date fechaFinal;

    public Long getIdAporte(){
        return idAporte;
    }

    public void setIdAporte(Long idAporte){
        this.idAporte = idAporte;
    }

    public BigDecimal getMonto(){
        return monto;
    }

    public void setMonto(BigDecimal monto){
        this.monto = monto;
    }

    public Date getFechaInicio(){
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal(){
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal){
        this.fechaFinal = fechaFinal;
    }


}
