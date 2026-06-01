import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { DatosAsociados } from "../domain/DatosAsociados";
import { PuestoEmpresa } from "../domain/PuestoEmpresa";

@Injectable({
    providedIn: 'root'
})
export class DatosAsociadosService{

    private url:string = 'http://localhost:8080/api/datos-asociados';

    constructor(private http: HttpClient){

    }

    listadoDatosAsociados(): Observable<DatosAsociados[]>{
        return this.http.get<DatosAsociados[]>(this.url);
    }
    
        
    buscarDatosAsociadosPorId(id: number): Observable<DatosAsociados>{
        return this.http.get<DatosAsociados>(`${this.url}/${id}`);
    }
    
    guardarDatosAsociados(datosAsociados: DatosAsociados) {
        return this.http.post(`${this.url}/guardar`, datosAsociados);
    }
        
    actualizarDatosAsociados(datosAsociados: DatosAsociados) {
        return this.http.put(`${this.url}/actualizar`, datosAsociados);
    }

    listadoDatosAsociadosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    listadoDatosAsociadosCompletos(): Observable<DatosAsociados[]>{
        return this.http.get<DatosAsociados[]>(`${this.url}/completos`);
    }

}