import { Estado } from "./Estado";
import { Usuario } from "./Usuario";

export class Prestamo{

    idPrestamo!: number;
    montoSolicitado?: number;
    fechaAprobacion?: Date;
    saldoPendiente?: number;
    tasaIntereses?: number;
    plazoMeses?: number;


    usuarioId?: number;
    identificacion?: number;
    nombreUsuario?: string;
    apellidoPaterno?: string;

    estadoId?: number;
    nombreEstado?: string;
    

};