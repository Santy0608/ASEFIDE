import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PuestoEmpresa } from "../domain/PuestoEmpresa";

@Injectable({
    providedIn: 'root'
})
export class PuestoEmpresaService{

    private url:string = 'http://localhost:8080/api/puestos-empresas';

    constructor(private http: HttpClient){

    }

    listadoPuestoEmpresas(): Observable<PuestoEmpresa[]>{
        return this.http.get<PuestoEmpresa[]>(this.url);
    }
        
    buscarPuestoEmpresaPorId(id: number): Observable<PuestoEmpresa>{
        return this.http.get<PuestoEmpresa>(`${this.url}/${id}`);
    }

    guardarPuestoEmpresa(puestoEmpresa: PuestoEmpresa) {
        return this.http.post(`${this.url}/guardar`, puestoEmpresa);
    }
    
    actualizarPuestoEmpresa(puestoEmpresa: PuestoEmpresa) {
        return this.http.put(`${this.url}/actualizar`, puestoEmpresa);
    }
    
    eliminarPuestoEmpresa(idPuestoEmpresa: number){
        return this.http.delete(`${this.url}/${idPuestoEmpresa}`);
    }

    
    listadoPuestoEmpresaPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    buscarPuestoEmpresa(puestoEmpresa: string): Observable<PuestoEmpresa[]>{
        const params = new HttpParams().set('puestoEmpresa', puestoEmpresa);

        return this.http.get<PuestoEmpresa[]>(`${this.url}/buscar`, {params});
    }

    listadoPuestosEmpresasCompletos(): Observable<PuestoEmpresa[]>{
        return this.http.get<PuestoEmpresa[]>(`${this.url}/completos`);
    }


}