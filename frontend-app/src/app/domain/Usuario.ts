import { AporteUsuario } from "./AporteUsuario";
import { Correo } from "./Correo";
import { DatosAsociados } from "./DatosAsociados";
import { Estado } from "./Estado";
import { Telefono } from "./Telefono";

export class Usuario{

    idUsuario: number = 0;
    identificacion?: string;
    nombre?: string;
    apellidoPaterno?: string;
    apellidoMaterno?: string;
    nombreUsuario?: string;
    contrasenia?: string;


    estadoId?: number;
    nombreEstado?: string;
    cantidadUsuarios?: number;
    estadoUsuario?: string;

    identificacionDatosAsociados?: number;
    aportes?: AporteUsuario[];
    fechaAfiliacion?: Date;

    direccionId?: number;
    distrito?: string;   

    admin?: boolean = false;
    asociado?: boolean = false;


    correos?: string[]; 
    numerosTelefono?: string[];

    correosIds?: number[];
    numerosIds?: number[];

}