import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PagosPrestamos } from "../domain/PagosPrestamos";


@Injectable({
    providedIn: 'root'
})
export class PagosPrestamosService{

    private url:string = 'http://localhost:8080/api/pagos-prestamos';

    constructor(private http: HttpClient){

    }

    listadoPagosPrestamos(): Observable<PagosPrestamos[]>{
        return this.http.get<PagosPrestamos[]>(this.url);
    }
    
        
    buscarPagosPrestamosPorId(id: number): Observable<PagosPrestamos>{
        return this.http.get<PagosPrestamos>(`${this.url}/${id}`);
    }

    guardarPagoPrestamo(pagosPrestamos: PagosPrestamos){
        return this.http.post('http://localhost:8080/api/pagos-prestamos/guardar', pagosPrestamos);
    }
            
    editarPagosPrestamos(pagosPrestamos: PagosPrestamos) {
        return this.http.put(`${this.url}/actualizar`, pagosPrestamos);
    }
     
    listadoPagosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    listadoPagosPrestamosCompletos(): Observable<PagosPrestamos[]>{
        return this.http.get<PagosPrestamos[]>(`${this.url}/completos`);
    }
}
