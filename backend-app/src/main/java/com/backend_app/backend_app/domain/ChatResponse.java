package com.backend_app.backend_app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponse {

    private String mensaje;
    private boolean mensajeExitoso;
    private String error;


    public ChatResponse(String mensaje, boolean mensajeExitoso, String error){
        this.mensaje = mensaje;
        this.mensajeExitoso = mensajeExitoso;
        this.error = error;
    }

    public static ChatResponse ok(String mensaje) {
        return new ChatResponse(mensaje, true, null);
    }

    public static ChatResponse error(String error) {
        return new ChatResponse(null, false, error);
    }

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }

    public boolean isMensajeExitoso(){
        return mensajeExitoso;
    }

    public void setMensajeExitoso(boolean mensajeExitoso){
        this.mensajeExitoso = mensajeExitoso;
    }

    public String getError(){
        return error;
    }

    public void setError(String erro){
        this.error = erro;
    }

}

