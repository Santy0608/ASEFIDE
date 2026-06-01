package com.backend_app.backend_app.dto;

public class ResultadosReporteDTO {

    private Long idResultado;

    private Long reporteId;
    private Integer totalRegistros;

    private String metricaNombre;

    private String metricaValor;

    private String tipoReporte;

    public String getTipoReporte(){
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte){
        this.tipoReporte = tipoReporte;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Long getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Long idResultado) {
        this.idResultado = idResultado;
    }

    public Long getReporteId() {
        return reporteId;
    }

    public void setReporteId(Long reporteId) {
        this.reporteId = reporteId;
    }

    public String getMetricaNombre() {
        return metricaNombre;
    }

    public void setMetricaNombre(String metricaNombre) {
        this.metricaNombre = metricaNombre;
    }

    public String getMetricaValor() {
        return metricaValor;
    }

    public void setMetricaValor(String metricaValor) {
        this.metricaValor = metricaValor;
    }
}
