import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Direccion } from "../domain/Direccion";


@Injectable({
    providedIn: 'root'
})
export class DireccionService{

    private url:string = 'http://localhost:8080/api/direcciones';

    constructor(private http: HttpClient){

    }

    listadoDirecciones(): Observable<Direccion[]>{
        return this.http.get<Direccion[]>(this.url);
    }
    
        
    buscarDireccionPorId(id: number): Observable<Direccion>{
        return this.http.get<Direccion>(`${this.url}/${id}`);
    }

    guardarDireccion(direccion: Direccion){
        return this.http.post('http://localhost:8080/api/direcciones/guardar', direccion);
    }
    
    editarDireccion(direccion: Direccion) {
        return this.http.put(`${this.url}/actualizar`, direccion);
    }
    
    eliminarDireccion(idDireccion: number){
        return this.http.delete(`${this.url}/${idDireccion}`);
    }

    listadoDireccionPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }  

    buscarDireccionPorProvincia(provincia: string): Observable<Direccion[]>{
        const params = new HttpParams().set('provincia', provincia);
            
        return this.http.get<Direccion[]>(`${this.url}/buscar`, { params });
    }

    listadoDireccionesCompletas(): Observable<Direccion[]>{
        return this.http.get<Direccion[]>(`${this.url}/completas`);
    }

}