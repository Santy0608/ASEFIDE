import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Transaccion } from "../domain/Transaccion";
import { Usuario } from "../domain/Usuario";

@Injectable({
    providedIn: 'root'
})
export class TransaccionService{

    private url: string = 'http://localhost:8080/api/transacciones';

    constructor(private http: HttpClient){

    }

    listadoTransacciones(): Observable<Transaccion[]>{
        return this.http.get<Transaccion[]>(this.url);
    }

    buscarTransaccionPorId(id: number): Observable<Transaccion>{
        return this.http.get<Transaccion>(`${this.url}/${id}`);
    }

    registrarUsuario(usuario: any): Observable<any>{
        return this.http.post('http://localhost:8080/api/usuarios/guardar', usuario);
    }


    guardarTransaccion(transaccion: Transaccion){
        return this.http.post('http://localhost:8080/api/transacciones/guardar', transaccion);
    }
    
    editarTransaccion(transaccion: Transaccion) {
        return this.http.put(`${this.url}/actualizar`, transaccion);
    }
    
    eliminarTransaccion(idTransaccion: number){
        return this.http.delete(`${this.url}/${idTransaccion}`);
    }

    listadoTransaccionesPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }
    
    getCantidadTransacciones(usuarioId: number): Observable<number> {
        return this.http.get<number>(
            `${this.url}/cantidad/${usuarioId}`
        );
    }

    top5Transacciones(): Observable<Transaccion[]> {
        return this.http.get<Transaccion[]>(`${this.url}/top5`);
    }

    historialTransacciones(idUsuario: number): Observable<Transaccion[]> {
        return this.http.get<Transaccion[]>(`${this.url}/historial/${idUsuario}`);
    }

    historialTransaccionesVM(): Observable<Transaccion[]>{
        return this.http.get<Transaccion[]>(`${this.url}/historial-transacciones`);
    }

    listadoTransaccionesCompletos(): Observable<Transaccion[]>{
        return this.http.get<Transaccion[]>(`${this.url}/completos`);
    }

}