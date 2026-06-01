import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Beneficio } from "../domain/Beneficio";


@Injectable({
    providedIn: 'root'
})
export class BeneficioService{

    private url: string = 'http://localhost:8080/api/beneficios';

    constructor(private http: HttpClient){

    }

    listadoBeneficios(): Observable<Beneficio[]>{
        return this.http.get<Beneficio[]>(this.url);
    }
    
    buscarBeneficioPorId(id: number): Observable<Beneficio>{
        return this.http.get<Beneficio>(`${this.url}/${id}`);
    }

    guardarActividad(beneficio: Beneficio){
        return this.http.post('http://localhost:8080/api/beneficios/guardar', beneficio);
    }
        
    editarBeneficio(beneficio: Beneficio) {
        return this.http.put(`${this.url}/actualizar`, beneficio);
    }
        
    eliminarBeneficio(idBeneficio: number){
        return this.http.delete(`${this.url}/${idBeneficio}`);
    }

    listadoBeneficiosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    listadoBeneficiosAsociados(): Observable<Beneficio[]>{
        return this.http.get<Beneficio[]>(`${this.url}/beneficios-asociados`);
    }

    buscarBeneficioPorNombre(nombreBeneficio: string): Observable<Beneficio[]>{
        const params = new HttpParams().set('nombreBeneficio', nombreBeneficio);
                    
        return this.http.get<Beneficio[]>(`${this.url}/buscar`, { params });
    }

    listadoBeneficiosCompletos(): Observable<Beneficio[]>{
        return this.http.get<Beneficio[]>(`${this.url}/completos`);
    }

}