import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ResultadoReporte } from "../domain/ResultadosReporte";


@Injectable({
    providedIn: 'root'
})
export class ResultadoReporteService{

    private url:string = 'http://localhost:8080/api/resultados-reportes';

    constructor(private http: HttpClient){

    }

    listadoResultadosReportes(): Observable<ResultadoReporte[]>{
        return this.http.get<ResultadoReporte[]>(this.url);
    }
    
        
    buscarResultadoReportePorId(id: number): Observable<ResultadoReporte>{
        return this.http.get<ResultadoReporte>(`${this.url}/${id}`);
    }
    
    guardarResultadoReporte(resultadoReporte: ResultadoReporte){
        return this.http.post('http://localhost:8080/api/resultados-reportes/guardar', resultadoReporte);
    }
        
    editarResultadoReporte(resultadoReporte: ResultadoReporte) {
        return this.http.put(`${this.url}/actualizar`, resultadoReporte);
    }

    listadoResultadosReportePaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    listadoResultadosReportesCompletos(): Observable<ResultadoReporte[]>{
        return this.http.get<ResultadoReporte[]>(`${this.url}/completos`);
    }

}