import { Estado } from "./Estado";
import { ModuloReporte } from "./ModuloReporte";
import { TipoReporte } from "./TipoReporte";
import { Usuario } from "./Usuario";

export class Reporte{

    idReporte?: number;
    fechaInicio?: Date;
    fechaFinal?: Date;

    tipoReporteId?: number;
    nombreTipoReporte?: string;

    moduloReporteId?: number;
    nombreModuloReporte?: string;

    estadoId?: number;
    nombreEstado?: string;

    usuarioId?: number;
    identificacion?: number;
    nombreUsuario?: string;
    apellidoPaterno?: string;

    resumenMontos?: number;
    fechaGeneracion?: Date;

    totalRegistros?: number;

}