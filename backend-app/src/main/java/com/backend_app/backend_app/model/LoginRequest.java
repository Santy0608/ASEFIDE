package com.backend_app.backend_app.model;

public class LoginRequest {

    private String nombreUsuario;
    private String contrasenia;

    public LoginRequest(){

    }

    public LoginRequest(String nombreUsuario, String contrasenia){
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia(){
        return contrasenia;
    }

    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }

}
