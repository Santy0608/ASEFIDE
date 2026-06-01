import { Estado } from "./Estado";

export class TipoTransaccion{
    idTipoTransaccion!: number;
    nombre?: string;
    descripcion?: string;

    estadoId?: number;
    nombreEstado?: string;
}