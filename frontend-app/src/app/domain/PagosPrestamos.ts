import { Prestamo } from "./Prestamo";
import { Transaccion } from "./Transaccion";

export class PagosPrestamos{

    idPago!: number;
    montoAbonado?: number;
    fechaPago?: Date;

    prestamoId?: number;
    montoSolicitado?: number;

    transaccionId?: number;
    montoTotal?: number;
    fechaTransaccion?: Date;

    nombreUsuario?: string;
    

}