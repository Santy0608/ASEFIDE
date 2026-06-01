import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Telefono } from "../domain/Telefono";


@Injectable({
    providedIn: 'root'
})
export class TelefonoService{

    private url:string = 'http://localhost:8080/api/telefonos';

    constructor(private http: HttpClient){

    }

    listadoTelefonos(): Observable<Telefono[]>{
        return this.http.get<Telefono[]>(this.url);
    }
    
        
    buscarTelefonoPorId(id: number): Observable<Telefono>{
        return this.http.get<Telefono>(`${this.url}/${id}`);
    }
    
     guardarTelefono(telefono: Telefono){
        return this.http.post('http://localhost:8080/api/telefonos/guardar', telefono);
     }
        
    editarTelefono(telefono: Telefono) {
        return this.http.put(`${this.url}/actualizar`, telefono);
    }
        
    eliminarTelefono(idTelefono: number){
        return this.http.delete(`${this.url}/${idTelefono}`);
    }

    listadoTelefonosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    buscarPorNumeroTelefono(numeroTelefono: string): Observable<Telefono[]> {
        const params = new HttpParams().set('numeroTelefono', numeroTelefono);
        
        return this.http.get<Telefono[]>(`${this.url}/buscar`, { params });
    }

    listadoTelefonosCompletos(): Observable<Telefono[]>{
        return this.http.get<Telefono[]>(`${this.url}/completos`);
    }

}