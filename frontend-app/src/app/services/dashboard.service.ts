import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { DashboardKpi } from "../domain/DashboardKpi";
import { Observable } from "rxjs";
import { DashboardTransaccion } from "../domain/DashboardTransaccion";
import { DashboardTipoAhorro } from "../domain/DashboardTipoAhorro";
import { DashboardPrestamoEstado } from "../domain/DashboardPrestamosEstado";
import { DashboardActividadesProximas } from "../domain/DashboardActividadesProximas";


@Injectable({
    providedIn: 'root'
})
export class DashboardService{

    private url: string = 'http://localhost:8080/api/dashboard';

    constructor(private http: HttpClient){

    }

    getKPIs() {
        return this.http.get<DashboardKpi>(
            `${this.url}/Kpis`
        );
    }

    obtenerDashboardTransacciones(): Observable<DashboardTransaccion[]>{
        return this.http.get<DashboardTransaccion[]>(`${this.url}/transacciones`);
    }

    obtenerDashboardTiposAhorros(): Observable<DashboardTipoAhorro[]>{
        return this.http.get<DashboardTipoAhorro[]>(`${this.url}/tipos-ahorros`);
    }

    obtenerDashboardPrestamosEstado(): Observable<DashboardPrestamoEstado[]>{
        return this.http.get<DashboardPrestamoEstado[]>(`${this.url}/prestamos-estado`);
    }

    obtenerDashboardActividadesProximas(): Observable<DashboardActividadesProximas[]>{
        return this.http.get<DashboardActividadesProximas[]>(`${this.url}/actividades-proximas`);
    }

}