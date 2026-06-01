package com.backend_app.backend_app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "FIDE_USUARIO_TB")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Column(name = "CONTRASENIA")
    private String contrasenia;

    @Transient
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    @Transient
    private boolean asociado;

    @ManyToMany(fetch = FetchType.EAGER) // EAGER es importante para que carguen al hacer login
    @JoinTable(
            name = "FIDE_USUARIOS_ROLES_TB",
            joinColumns = @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    )
    private List<Rol> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DIRECCION")
    private Direccion direccion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DATOS_ASOCIADOS", referencedColumnName = "ID_DATOS_ASOCIADOS")
    private DatosAsociados datosAsociados;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "FIDE_USUARIOS_TELEFONOS_TB",
            joinColumns = @JoinColumn(name = "USUARIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "NUMERO_ID")
    )
    private List<Telefono> telefonos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "FIDE_USUARIOS_CORREOS_TB",
            joinColumns = @JoinColumn(name = "USUARIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "CORREO_ID")
    )
    private List<Correo> correos;

    public List<Telefono> getTelefonos(){
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos){
        this.telefonos = telefonos;
    }

    public List<Correo> getCorreos(){
        return correos;
    }

    public void setCorreos(List<Correo> correos){
        this.correos = correos;
    }

    public List<Rol> getRoles(){
        return roles;
    }

    public void setRoles(List<Rol> roles){
        this.roles = roles;
    }

    public Direccion getDireccion(){
        return direccion;
    }

    public void setDireccion(Direccion direccion){
        this.direccion = direccion;
    }

    public String getIdentificacion(){
        return identificacion;
    }

    public void setIdentificacion(String identificacion){
        this.identificacion = identificacion;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellidoPaterno(){
        return apellidoPaterno;
    }

    public String getApellidoMaterno(){
        return apellidoMaterno;
    }


    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
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

    public DatosAsociados getDatosAsociados(){
        return datosAsociados;
    }

    public void setDatosAsociados(DatosAsociados datosAsociados){
        this.datosAsociados = datosAsociados;
    }

    public Long getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario){
        this.idUsuario = idUsuario;
    }


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public boolean isAsociado(){
        return asociado;
    }

    public void setAsociado(boolean asociado){
        this.asociado = asociado;
    }
}
