import { Estado } from "./Estado";
import { TipoAhorro } from "./TipoAhorro";
import { Usuario } from "./Usuario";

export class CuentasAhorro{

    idAhorro!: number;
    montoAporte?: number;
    fechaApertura?: Date;
    saldoActual?: number;
    totalAhorro?: number;

    estadoId?: number;
    nombreEstado?: string;

    tipoAhorroId?: number;
    nombreTipoAhorro?: string;

    usuarioId?: number;
    nombreUsuario?: string;
    apellidoPaterno?: string;

}