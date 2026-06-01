import { Categoria } from "./Categoria";
import { Estado } from "./Estado";

export class Beneficio{

    idBeneficio!: number;
    nombreBeneficio?: string;
    descripcion?: string;

    categoriaId?: number;
    nombreCategoria?:string;

    estadoId?: number;
    nombreEstado?: string;
    
    imagenUrl?: string;

}