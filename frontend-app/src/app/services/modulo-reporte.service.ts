import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ModuloReporte } from "../domain/ModuloReporte";


@Injectable({
    providedIn: 'root'
})
export class ModuloReporteService{

    private url:string = 'http://localhost:8080/api/modulo-reporte';

    constructor(private http: HttpClient){

    }

    listadoModuloReportes(): Observable<ModuloReporte[]>{
        return this.http.get<ModuloReporte[]>(this.url);
    }
    
        
    buscarModuloReportePorId(id: number): Observable<ModuloReporte>{
        return this.http.get<ModuloReporte>(`${this.url}/${id}`);
    }
    
    guardarModuloReporte(moduloReporte: ModuloReporte){
        return this.http.post('http://localhost:8080/api/modulo-reporte/guardar', moduloReporte);
    }
        
    editarModuloReporte(moduloReporte: ModuloReporte) {
        return this.http.put(`${this.url}/actualizar`, moduloReporte);
    }
        
    eliminarModuloReporte(idModuloReporte: number){
        return this.http.delete(`${this.url}/${idModuloReporte}`);
    }

    listadoModuloReportePaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    buscarModuloReportePorNombre(nombreModuloReporte: string): Observable<ModuloReporte[]>{
        const params = new HttpParams().set('nombreModuloReporte', nombreModuloReporte);
                        
        return this.http.get<ModuloReporte[]>(`${this.url}/buscar`, { params });
    }

    listadoModulosReportesCompletos(): Observable<ModuloReporte[]>{
        return this.http.get<ModuloReporte[]>(`${this.url}/completos`);
    }

}