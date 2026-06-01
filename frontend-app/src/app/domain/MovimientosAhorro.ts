import { CuentasAhorro } from "./CuentasAhorro";
import { Transaccion } from "./Transaccion";

export class MovimientosAhorro{
    idMovimiento!: number;
    monto?: number;
    fechaDeposito?: Date;

    cuentasAhorroId?: number;
    montoAporte?: number;

    transaccionId?: number;
    montoTota?: number;

    nombreUsuario?: string;
    fechaTransaccion?: Date;

    tipoMovimiento?: string;
}