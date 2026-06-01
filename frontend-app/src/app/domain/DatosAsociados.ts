import { AporteUsuario } from "./AporteUsuario";
import { PuestoEmpresa } from "./PuestoEmpresa";
import { Usuario } from "./Usuario";

export class DatosAsociados{

    idDatosAsociados!: number;
    fechaAfiliacion?: Date;
    aportes?: AporteUsuario[];

    puestoEmpresaId?: number;
    nombrePuestoEmpresa?: string;



    //Datos para vista de datos asociados
    idUsuario?: number;
    
    nombreCompleto?: string;
    nombreUsuario?: string;
    cantidadAportes?: number;
    aporteVigente?: number;
    totalAportes?: number;
    puestoEmpresa?: string;
    estadoUsuario?: string;

}