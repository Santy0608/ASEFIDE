import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LugarEvento } from "../domain/LugarEvento";

@Injectable({
    providedIn: 'root'
})
export class LugarEventoService{

    private url:string = 'http://localhost:8080/api/lugar-evento';

    constructor(private http: HttpClient){

    }

    listadoLugaresEventos(): Observable<LugarEvento[]>{
        return this.http.get<LugarEvento[]>(this.url);
    }
    
        
    buscarLugarEventoPorId(id: number): Observable<LugarEvento>{
        return this.http.get<LugarEvento>(`${this.url}/${id}`);
    }

    guardarLugarEvento(lugarEvento: LugarEvento){
        return this.http.post('http://localhost:8080/api/lugar-evento/guardar', lugarEvento);
    }
    
    editarLugarEvento(lugarEvento: LugarEvento) {
        return this.http.put(`${this.url}/actualizar`, lugarEvento);
    }
    
    eliminarLugarEvento(idLugarEvento: number){
        return this.http.delete(`${this.url}/${idLugarEvento}`);
    }

    listadoLugaresEventosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   
    
    buscarLugarEventoPorNombre(nombreLugarEvento: string): Observable<LugarEvento[]>{
        const params = new HttpParams().set('nombreLugarEvento', nombreLugarEvento);
                    
        return this.http.get<LugarEvento[]>(`${this.url}/buscar`, { params });
    }

    listadoLugaresEventosCompletos(): Observable<LugarEvento[]>{
        return this.http.get<LugarEvento[]>(`${this.url}/completos`)
    }

}