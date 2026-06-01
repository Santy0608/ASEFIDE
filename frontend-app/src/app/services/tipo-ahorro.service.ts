import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TipoAhorro } from "../domain/TipoAhorro";


@Injectable({
    providedIn: 'root'
})
export class TipoAhorroService{

    private url:string = 'http://localhost:8080/api/tipos-ahorros';

    constructor(private http: HttpClient){

    }

    listadoTiposAhorros(): Observable<TipoAhorro[]>{
        return this.http.get<TipoAhorro[]>(this.url);
    }
    
        
    buscarTipoAhorroPorId(id: number): Observable<TipoAhorro>{
        return this.http.get<TipoAhorro>(`${this.url}/${id}`);
    }

    guardarTipoAhorro(tipoAhorro: TipoAhorro){
        return this.http.post('http://localhost:8080/api/tipos-ahorros/guardar', tipoAhorro);
    }
    
    editarTipoAhorro(tipoAhorro: TipoAhorro) {
        return this.http.put(`${this.url}/actualizar`, tipoAhorro);
    }
    
    eliminarTipoAhorro(idTipoAhorro: number){
        return this.http.delete(`${this.url}/${idTipoAhorro}`);
    }    

    listadoTipoAhorroPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    buscarTipoAhorroPorNombre(nombreTipoAhorro: string): Observable<TipoAhorro[]> {
        const params = new HttpParams().set('nombreTipoAhorro', nombreTipoAhorro);
    
        return this.http.get<TipoAhorro[]>(`${this.url}/buscar`, { params });
    }

    listadoTiposAhorrosCompletos(): Observable<TipoAhorro[]>{
        return this.http.get<TipoAhorro[]>(`${this.url}/completos`);
    }

}