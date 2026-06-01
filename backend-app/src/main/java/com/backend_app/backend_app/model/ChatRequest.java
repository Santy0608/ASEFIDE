package com.backend_app.backend_app.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {

    private String mensaje;
    private List<MensajeHistorial> historial;

    @Data
    public static class MensajeHistorial {
        private String rol;
        private String contenido;

        public String getRol(){
            return rol;
        }

        public void setRol(String rol){
            this.rol = rol;
        }

        public String getContenido(){
            return contenido;
        }

        public void setContenido(String contenido){
            this.contenido = contenido;
        }

    }

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }

    public List<MensajeHistorial> getHistorial(){
        return historial;
    }

    public void setHistorial(List<MensajeHistorial> historial){
        this.historial = historial;
    }

}
