import { Estado } from "./Estado";

export class TipoReporte{

    idTipoReporte!: number;
    nombre?: string;
    
    estadoId?: Estado;
    nombreEstado?: string;
}