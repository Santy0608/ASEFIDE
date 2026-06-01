import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Rol } from "../domain/Rol";

@Injectable({
    providedIn: 'root'
})
export class RolService{

    private url:string = 'http://localhost:8080/api/roles';

    constructor(private http: HttpClient){

    }

    listadoRoles(): Observable<Rol[]>{
        return this.http.get<Rol[]>(this.url);
    }
    
        
    buscarRolPorId(id: number): Observable<Rol>{
        return this.http.get<Rol>(`${this.url}/${id}`);
    }
    
    guardarRol(rol: Rol){
        return this.http.post('http://localhost:8080/api/roles/guardar', rol);
    }
    
    editarRol(rol: Rol) {
        return this.http.put(`${this.url}/actualizar`, rol);
    }
    
    eliminarRol(idRol: number){
        return this.http.delete(`${this.url}/${idRol}`);
    }

    listadoRolPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }   

    listadoRolesCompletos(): Observable<Rol[]>{
        return this.http.get<Rol[]>(`${this.url}/completos`);
    }

}