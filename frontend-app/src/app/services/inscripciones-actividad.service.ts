import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { InscripcionesActividad } from "../domain/inscripciones-actividad";


@Injectable({
    providedIn: 'root'
})
export class InscripcionPorActividadService{

    private url:string = 'http://localhost:8080/api/inscripciones-actividades';

    constructor(private http: HttpClient){

    }

    listadoInscripcionesActividad(): Observable<InscripcionesActividad[]>{
        return this.http.get<InscripcionesActividad[]>(this.url);
    }
    
        
    buscarInscripcionPorActividadPorId(id: number): Observable<InscripcionesActividad>{
        return this.http.get<InscripcionesActividad>(`${this.url}/${id}`);
    }

        
    guardarInscripcion(inscripcionActividad: InscripcionesActividad){
        return this.http.post(`${this.url}/guardar`, inscripcionActividad);
    }
    
    editarInscripcion(inscripcionActividad: InscripcionesActividad) {
        return this.http.put(`${this.url}/actualizar`, inscripcionActividad);
    }
    
    eliminarInscripcion(idInscripcion: number){
        return this.http.delete(`${this.url}/${idInscripcion}`);
    }
    
    listadoInscripcionesActividadesPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }  

    listadoInscripcionesActividadesCompletas(): Observable<InscripcionesActividad[]>{
        return this.http.get<InscripcionesActividad[]>(`${this.url}/completas`);
    }

}