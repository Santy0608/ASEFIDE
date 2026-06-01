import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { DetalleTransaccion } from "../domain/DetallesTransaccion";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class DetalleTransaccionService{

    private url:string = 'http://localhost:8080/api/detalle-transaccion';

    constructor(private http: HttpClient){

    }

    listadoDetalleTransaccion(): Observable<DetalleTransaccion[]>{
        return this.http.get<DetalleTransaccion[]>(this.url);
    }
    
        
    buscarDetalleTransaccionPorId(id: number): Observable<DetalleTransaccion>{
        return this.http.get<DetalleTransaccion>(`${this.url}/${id}`);
    }

    guardarDetalleTransaccion(detalleTransaccion: DetalleTransaccion){
        return this.http.post('http://localhost:8080/api/detalle-transaccion/guardar', detalleTransaccion);
    }
        
    editarDetalleTransaccion(detalleTransaccion: DetalleTransaccion) {
        return this.http.put(`${this.url}/actualizar`, detalleTransaccion);
    }
 
    listadoDetalleTransaccionPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }  

    listadoDetallesTransaccionesCompletas(): Observable<DetalleTransaccion[]>{
        return this.http.get<DetalleTransaccion[]>(`${this.url}/completas`);
    }

    

}