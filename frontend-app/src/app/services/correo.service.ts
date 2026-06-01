import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Correo } from "../domain/Correo";


@Injectable({
    providedIn: 'root'
})
export class CorreoService{

    private url:string = 'http://localhost:8080/api/correos';


    constructor(private http: HttpClient){

    }

    listadoCorreos(): Observable<Correo[]>{
        return this.http.get<Correo[]>(this.url);
    }
    
        
    buscarCorreoPorId(id: number): Observable<Correo>{
        return this.http.get<Correo>(`${this.url}/${id}`);
    }

    guardarCorreo(correo: Correo){
        return this.http.post('http://localhost:8080/api/correos/guardar', correo);
    }
    
    editarCorreo(correo: Correo) {
        return this.http.put(`${this.url}/actualizar`, correo);
    }
    
    eliminarCorreo(idCorreo: number){
        return this.http.delete(`${this.url}/${idCorreo}`);
    }
    
    listadoCorreosPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    buscarCorreo(correoElectronico: string): Observable<Correo[]>{
        const params = new HttpParams().set('correoElectronico', correoElectronico);
        
        return this.http.get<Correo[]>(`${this.url}/buscar`, { params });
    }

    listadoCorreosCompletos(): Observable<Correo[]>{
        return this.http.get<Correo[]>(`${this.url}/completos`);
    }

}