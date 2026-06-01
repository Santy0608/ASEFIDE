import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Prestamo } from "../domain/Prestamo";
import { Estado } from "../domain/Estado";


@Injectable(
    {
        providedIn: 'root'
    }
)
export class PrestamoService{

    private url: string = 'http://localhost:8080/api/prestamos';

    constructor(private http: HttpClient){

    }

    listadoPrestamos(): Observable<Prestamo[]>{
        return this.http.get<Prestamo[]>(this.url);
    }

    buscarPrestamoPorId(id: number): Observable<Prestamo>{
        return this.http.get<Prestamo>(`${this.url}/${id}`);
    }

    guardarPrestamo(prestamo: Prestamo){
        return this.http.post('http://localhost:8080/api/prestamos/guardar', prestamo);
    }
    
    editarPrestamo(prestamo: Prestamo) {
        return this.http.put(`${this.url}/actualizar`, prestamo);
    }
    
    eliminarPrestamo(idPrestamo: number){
        return this.http.delete(`${this.url}/${idPrestamo}`);
    }

    listadoPrestamosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    obtenerEstadoPrestamos(): Observable<Estado[]>{
        return this.http.get<Estado[]>(`${this.url}/estado`);
    }

    listadoPrestamosCompletos(): Observable<Prestamo[]>{
        return this.http.get<Prestamo[]>(`${this.url}/completos`)
    }

    prestamosPorEstado(): Observable<Estado[]> {
        return this.http.get<Estado[]>(`${this.url}/por-estado`);
    }

}