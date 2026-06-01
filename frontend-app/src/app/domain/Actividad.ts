import { Estado } from "./Estado";
import { Usuario } from "./Usuario";

export class Actividad{

    idActividad!: number;
    nombre?: string;
    descripcion?: string;
    fechaEvento?: Date;
    cupoTotal?: number;

    estadoId?: number;
    nombreEstado?: string;
    estadoActividad?: string;

    usuarioId?: number;
    nombreUsuario?: string;
    apellidoPaterno?: string;

    lugarEventoId?: number;
    nombreLugarEvento?: string;

    imagenUrl?: string;

}