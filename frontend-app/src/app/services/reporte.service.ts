import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Reporte } from "../domain/Reporte";


@Injectable({
    providedIn: 'root'
})
export class ReporteService{

    private url: string = 'http://localhost:8080/api/reportes';

    constructor(private http: HttpClient){

    }

    listadoReportes(): Observable<Reporte[]>{
        return this.http.get<Reporte[]>(this.url);
    }

    buscarReportePorId(id: number): Observable<Reporte>{
        return this.http.get<Reporte>(`${this.url}/${id}`);
    }

    guardarReporte(reporte: Reporte){
        return this.http.post('http://localhost:8080/api/reportes/guardar', reporte);
    }
        
    editarReporte(reporte: Reporte) {
        return this.http.put(`${this.url}/actualizar`, reporte);
    }
        
    eliminarReporte(idReporte: number){
        return this.http.delete(`${this.url}/${idReporte}`);
    }

    listadoReportePaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    descargarReportePDF(fechaInicio: string, fechaFinal: string): Observable<Blob> {
        const params = new HttpParams()
        .set('fechaInicio', fechaInicio)
        .set('fechaFinal', fechaFinal);

        return this.http.get(`${this.url}/reporte/pdf`, { params, responseType: 'blob' });
    }

    listadoReportesCompletos(): Observable<Reporte[]>{
        return this.http.get<Reporte[]>(`${this.url}/completos`);
    }


}