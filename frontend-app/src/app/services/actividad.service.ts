import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Actividad } from "../domain/Actividad";
import { Beneficio } from "../domain/Beneficio";

@Injectable({
    providedIn: 'root'
})
export class ActividadService{

    private url:string = 'http://localhost:8080/api/actividades';

    constructor(private http: HttpClient){
        
    }

    listadoActividades(): Observable<Actividad[]>{
        return this.http.get<Actividad[]>(this.url);
    }

    buscarActividadPorId(id: number): Observable<Actividad>{
        return this.http.get<Actividad>(`${this.url}/${id}`);
    }

    guardarActividad(actividad: Actividad){
        return this.http.post('http://localhost:8080/api/actividades/guardar', actividad);
    }
    
    editarActividad(actividad: Actividad) {
        return this.http.put(`${this.url}/actualizar`, actividad);
    }
    
    eliminarActividad(idActividad: number){
        return this.http.delete(`${this.url}/${idActividad}`);
    }

    listadoActividadesPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    listadoActividadesAsociados(): Observable<Actividad[]>{
        return this.http.get<Actividad[]>(`${this.url}/actividades-asociados`);
    }

    buscarActividadPorNombre(nombre: string): Observable<Actividad[]>{
        const params = new HttpParams().set('nombre', nombre);
                
        return this.http.get<Actividad[]>(`${this.url}/buscar`, { params });
    }

    listadoActividadesCompletas(): Observable<Actividad[]>{
        return this.http.get<Actividad[]>(`${this.url}/completas`); 
    }

}
