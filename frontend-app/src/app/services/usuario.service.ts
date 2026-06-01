import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Usuario } from "../domain/Usuario";
import { Estado } from "../domain/Estado";
import { Aporte } from "../domain/Aporte";
import { AhorroUsuario } from "../domain/AhorroUsuario";
import { TransaccionUsuario } from "../domain/TransaccionUsuario";
import { PrestamoUsuario } from "../domain/PrestamoUsuario";
import { InscripcionUsuario } from "../domain/InscripcionUsuario";
import { InscripcionesActividad } from "../domain/inscripciones-actividad";
import { AporteAsociado } from "../domain/AporteAsociado";


@Injectable({
    providedIn: 'root'
})
export class UsuarioService{

    private url: string = 'http://localhost:8080/api/usuarios';

    constructor(private http: HttpClient){

    }

    listadoUsuarios(): Observable<Usuario[]>{
        return this.http.get<Usuario[]>(this.url);
    }

    buscarUsuarioPorId(id: number): Observable<Usuario>{
        return this.http.get<Usuario>(`${this.url}/${id}`);
    }

    guardarUsuario(usuario: Usuario){
        return this.http.post('http://localhost:8080/api/usuarios/guardar', usuario);
    }

    editarUsuario(usuario: Usuario) {
        return this.http.put(`${this.url}/actualizar`, usuario);
    }

    eliminarUsuario(idUsuario: number){
        return this.http.delete(`${this.url}/${idUsuario}`);
    }

    listadoUsuariosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    listarUsuarios(): Observable<number> {
        return this.http.get<number>(`${this.url}/listar`);
    }

    listarUsuariosCompletos(): Observable<Usuario[]>{
        return this.http.get<Usuario[]>(`${this.url}/completos`);
    }

    buscarPorNombre(nombre: string): Observable<Usuario[]> {
        const params = new HttpParams().set('nombre', nombre);
    
        return this.http.get<Usuario[]>(`${this.url}/buscar`, { params });
    }

    ordenarAlfabetico(): Observable<Usuario[]> {
        return this.http.get<Usuario[]>(`${this.url}/alfabetico`);
    }

    ordenarPorFecha(): Observable<Usuario[]> {
        return this.http.get<Usuario[]>(`${this.url}/por-fecha`);
    }

    usuariosUltimoMes(): Observable<Usuario[]> {
        return this.http.get<Usuario[]>(`${this.url}/ultimo-mes`);
    }

    listadoUsuariosInactivos(): Observable<Usuario[]>{
        return this.http.get<Usuario[]>(`${this.url}/inactivos`)
    }

    usuariosPorEstado(): Observable<Estado[]> {
        return this.http.get<Estado[]>(`${this.url}/por-estado`);
    }

    obtenerUsuariosUltimoMes(): Observable<Usuario[]>{
        return this.http.get<Usuario[]>(`${this.url}/ultimo-mes-usuarios`);
    }

    obtenerMisAportes(): Observable<AporteAsociado[]> {
        return this.http.get<AporteAsociado[]>(`${this.url}/mis-aportes`, {
            withCredentials: true
        });
    }

    descargarAportePdf(): Observable<Blob>{
        return this.http.get(`${this.url}/mis-aportes/descargar`, {responseType: 'blob'});
    }

    obtenerMisAhorros(): Observable<AhorroUsuario[]>{
        return this.http.get<AhorroUsuario[]>(`${this.url}/mis-ahorros`, {
            withCredentials: true
        });
    }

    obtenerMisTransacciones(): Observable<TransaccionUsuario[]>{
        return this.http.get<TransaccionUsuario[]>(`${this.url}/mis-transacciones`, {
            withCredentials: true
        });
    }

    obtenerMisPrestamos(): Observable<PrestamoUsuario[]>{
        return this.http.get<PrestamoUsuario[]>(`${this.url}/mis-prestamos`, {
            withCredentials: true
        });
    }

    obtenerMisInscripcionesActividades(): Observable<InscripcionUsuario[]>{
        return this.http.get<InscripcionUsuario[]>(`${this.url}/mis-inscripciones-actividades`, {
            withCredentials: true
        });
    }

}