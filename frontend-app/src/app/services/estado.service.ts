import { HttpBackend, HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Estado } from "../domain/Estado";


@Injectable({
    providedIn: 'root'
})
export class EstadoService{

    private url:string = 'http://localhost:8080/api/estados';

    constructor(private http: HttpClient){
        
    }

    listadoEstados(): Observable<Estado[]>{
        return this.http.get<Estado[]>(this.url);
    }

    buscarEstadoPorId(id: number): Observable<Estado>{
        return this.http.get<Estado>(`${this.url}/${id}`);
    }

    guardarEstado(estado: Estado){
        return this.http.post('http://localhost:8080/api/estados/guardar', estado);
    }
    
    editarEstado(estado: Estado) {
        return this.http.put(`${this.url}/actualizar`, estado);
    }
    
    eliminarEstado(idEstado: number){
        return this.http.delete(`${this.url}/${idEstado}`);
    }

    listadoEstadosCompletos(): Observable<Estado[]>{
        return this.http.get<Estado[]>(`${this.url}/completos`);
    }
    

}