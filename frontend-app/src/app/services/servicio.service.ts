import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Servicio } from "../domain/servicio";
import { HttpClient, HttpParams } from "@angular/common/http";


@Injectable({
    providedIn: 'root'
})
export class ServicioService{

    private url: string = 'http://localhost:8080/api/servicios';

    constructor(private http: HttpClient){

    }

    listadoServicios(): Observable<Servicio[]>{
        return this.http.get<Servicio[]>(this.url);
    }

    buscarServicioPorId(id: number): Observable<Servicio>{
        return this.http.get<Servicio>(`${this.url}/${id}`);
    }
    
    guardarServicio(servicio: Servicio){
        return this.http.post('http://localhost:8080/api/servicios/guardar', servicio);
    }
            
    editarServicio(servicio: Servicio) {
        return this.http.put(`${this.url}/actualizar`, servicio);
    }
            
    eliminarServicio(idServicio: number){
        return this.http.delete(`${this.url}/${idServicio}`);
    }

    listadoServicioPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    listadoServiciosAsociados(): Observable<Servicio[]>{
        return this.http.get<Servicio[]>(`${this.url}/servicios-asociados`)
    }

    buscarServicioPorNombre(nombreServicio: string): Observable<Servicio[]>{
        const params = new HttpParams().set('nombreServicio', nombreServicio);
                    
        return this.http.get<Servicio[]>(`${this.url}/buscar`, { params });
    }

    listadoServiciosCompletos(): Observable<Servicio[]>{
        return this.http.get<Servicio[]>(`${this.url}/completos`);
    }

}