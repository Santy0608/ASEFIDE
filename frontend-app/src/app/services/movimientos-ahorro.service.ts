import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { MovimientosAhorro } from "../domain/MovimientosAhorro";


@Injectable({
    providedIn: 'root'
})
export class MovimientosAhorroService{

    private url:string = 'http://localhost:8080/api/movimientos-ahorros';

    constructor(private http: HttpClient){

    }

    listadoMovimientosAhorro(): Observable<MovimientosAhorro[]>{
        return this.http.get<MovimientosAhorro[]>(this.url);
    }
    
        
    buscarMovimientoAhorroPorId(id: number): Observable<MovimientosAhorro>{
        return this.http.get<MovimientosAhorro>(`${this.url}/${id}`);
    }

    
    guardarMovimientoAhorro(movimientosAhorro: MovimientosAhorro){
        return this.http.post('http://localhost:8080/api/movimientos-ahorros/guardar', movimientosAhorro);
    }
                
    editarMovimientoAhorro(movimientosAhorro: MovimientosAhorro) {
        return this.http.put(`${this.url}/actualizar`, movimientosAhorro);
    }

    listadoMovimientosAhorroPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    listadoMovimientosAhorrosCompletos(): Observable<MovimientosAhorro[]>{
        return this.http.get<MovimientosAhorro[]>(`${this.url}/completos`);
    }

}
