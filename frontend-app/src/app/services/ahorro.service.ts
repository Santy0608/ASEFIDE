import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CuentasAhorro } from "../domain/CuentasAhorro";


@Injectable({
    providedIn: 'root'
})
export class AhorroService{

    private url: string = 'http://localhost:8080/api/cuentas-ahorro';

    constructor(private http: HttpClient){

    }

    listadoAhorros(): Observable<CuentasAhorro[]>{
        return  this.http.get<CuentasAhorro[]>(this.url);
    }

    buscarAhorroPorId(id: number): Observable<CuentasAhorro>{
        return this.http.get<CuentasAhorro>(`${this.url}/${id}`);
    }

    guardarCuentaAhorro(cuentaAhorro: CuentasAhorro){
        return this.http.post('http://localhost:8080/api/cuentas-ahorro/guardar', cuentaAhorro);
    }
    
    editarCuentaAhorro(cuentaAhorro: CuentasAhorro) {
        return this.http.put(`${this.url}/actualizar`, cuentaAhorro);
    }
    
    eliminarCuentaAhorro(idCuentaAhorro: number){
        return this.http.delete(`${this.url}/${idCuentaAhorro}`);
    }
    
    listadoAhorrosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    getTotalAhorros(): Observable<number> {
        return this.http.get<number>('http://localhost:8080/api/cuentas-ahorro/total');
    }

    getPromedioAhorros(): Observable<number>{
        return this.http.get<number>('http://localhost:8080/api/cuentas-ahorro/promedio-ahorros');
    }

    top10MasAhorros(): Observable<CuentasAhorro[]> {
        return this.http.get<CuentasAhorro[]>(`${this.url}/top10-mas`);
    }

    top10MenosAhorros(): Observable<CuentasAhorro[]> {
        return this.http.get<CuentasAhorro[]>(`${this.url}/top10-menos`);
    }

    usuariosMayorAhorro(monto: number): Observable<CuentasAhorro[]> {
        const params = new HttpParams().set('monto', monto.toString());
        return this.http.get<CuentasAhorro[]>(`${this.url}/mayor`, { params });
    }

    reporteAhorrosUsuario(idUsuario: number): Observable<CuentasAhorro[]> {
        return this.http.get<CuentasAhorro[]>(`${this.url}/reporte/${idUsuario}`);
    }

    listadoCuentasAhorrosCompletos(): Observable<CuentasAhorro[]>{
        return this.http.get<CuentasAhorro[]>(`${this.url}/completos`);
    }

}