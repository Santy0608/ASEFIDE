import { Categoria } from "./Categoria";
import { Estado } from "./Estado";

export class Servicio{

    idServicio!: number;
    nombreServicio?: string;
    descripcion?: string;
    valorEstimado?: number;
    stock?: number;

    categoriaId?: number;
    nombreCategoria?: string;
    estadoId?: number;
    nombreEstado?: string;

    imagenUrl?: string;
}