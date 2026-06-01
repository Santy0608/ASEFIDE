import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TipoTransaccion } from "../domain/TipoTransaccion";


@Injectable({
    providedIn: 'root'
})
export class TipoTransaccionService{

    private url:string = 'http://localhost:8080/api/tipo-transacciones';

    constructor(private http: HttpClient){

    }

    listadoTiposTransacciones(): Observable<TipoTransaccion[]>{
        return this.http.get<TipoTransaccion[]>(this.url);
    }
    
        
    buscarTipoTransaccionPorId(id: number): Observable<TipoTransaccion>{
        return this.http.get<TipoTransaccion>(`${this.url}/${id}`);
    }
   
    guardarTipoTransaccion(tipoTransaccion: TipoTransaccion){
        return this.http.post('http://localhost:8080/api/tipo-transacciones/guardar', tipoTransaccion);
    }
        
    editarTipoTransaccion(tipoTransaccion: TipoTransaccion) {
        return this.http.put(`${this.url}/actualizar`, tipoTransaccion);
    }
        
    eliminarTipoTransaccion(idTipoTransaccion: number){
        return this.http.delete(`${this.url}/${idTipoTransaccion}`);
    } 

    listadoTipoTransaccionPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    buscarTipoTransaccionPorNombre(nombreTipoTransaccion: string): Observable<TipoTransaccion[]> {
        const params = new HttpParams().set('nombreTipoTransaccion', nombreTipoTransaccion);
        
        return this.http.get<TipoTransaccion[]>(`${this.url}/buscar`, { params });
    }

    listadoTiposTransaccionesCompletas(): Observable<TipoTransaccion[]>{
        return this.http.get<TipoTransaccion[]>(`${this.url}/completas`);
    }

}