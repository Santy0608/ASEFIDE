import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TipoReporte } from "../domain/TipoReporte";


@Injectable({
    providedIn: 'root'
})
export class TipoReporteService{

    private url:string = 'http://localhost:8080/api/tipo-reportes';

    constructor(private http: HttpClient){

    }

    listadoTipoReportes(): Observable<TipoReporte[]>{
        return this.http.get<TipoReporte[]>(this.url);
    }
    
        
    buscarTipoReportePorId(id: number): Observable<TipoReporte>{
        return this.http.get<TipoReporte>(`${this.url}/${id}`);
    }

    guardarTipoReporte(tipoReporte: TipoReporte){
        return this.http.post('http://localhost:8080/api/tipo-reportes/guardar', tipoReporte);
    }
        
    editarTipoReporte(tipoReporte: TipoReporte) {
        return this.http.put(`${this.url}/actualizar`, tipoReporte);
    }
        
    eliminarTipoReporte(idTipoReporte: number){
        return this.http.delete(`${this.url}/${idTipoReporte}`);
    }  
    
    listadoTipoReportePaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

     buscarTipoReportePorNombre(nombreTipoReporte: string): Observable<TipoReporte[]> {
        const params = new HttpParams().set('nombreTipoReporte', nombreTipoReporte);
        
        return this.http.get<TipoReporte[]>(`${this.url}/buscar`, { params });
    }

    listadoTiposReportesCompletos(): Observable<TipoReporte[]>{
        return this.http.get<TipoReporte[]>(`${this.url}/completos`);
    }

}