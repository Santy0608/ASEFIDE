import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Categoria } from "../domain/Categoria";
import { Usuario } from "../domain/Usuario";


@Injectable({
    providedIn: 'root'
})
export class CategoriaService{

    private url:string = 'http://localhost:8080/api/categorias';

    constructor(private http: HttpClient){

    }

    listadoCagtegorias(): Observable<Categoria[]>{
        return this.http.get<Categoria[]>(this.url);
    }
    
        
    buscarCategoriaPorId(id: number): Observable<Categoria>{
        return this.http.get<Categoria>(`${this.url}/${id}`);
    }
    
    guardarCategoria(categoria: Categoria){
        return this.http.post('http://localhost:8080/api/categorias/guardar', categoria);
    }

    editarCategoria(categoria: Categoria) {
        return this.http.put(`${this.url}/actualizar`, categoria);
    }

    eliminarCategoria(idCategoria: number){
        return this.http.delete(`${this.url}/${idCategoria}`);
    }

    listadoCategoriaPaginacion(page: number): Observable<any>{
        return this.http.get<any[]>(`${this.url}/page/${page}`);
    }

    
    buscarCategoriaPorNombre(nombre: string): Observable<Categoria[]>{
        const params = new HttpParams().set('nombreCategoria', nombre);
                    
        return this.http.get<Categoria[]>(`${this.url}/buscar`, { params });
    }

    listadoCategoriasCompletas(): Observable<Categoria[]>{
        return this.http.get<Categoria[]>(`${this.url}/completas`);
    }

}