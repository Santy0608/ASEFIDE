import { Estado } from "./Estado";

export class Direccion{

    idDireccion!: number;
    provincia?: string;
    canton?: string;
    distrito?: string;

    estadoId?: number;
    nombreEstado?: string;

}