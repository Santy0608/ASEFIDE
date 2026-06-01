import { Estado } from "./Estado";
import { MovimientosAhorro } from "./MovimientosAhorro";
import { PagosPrestamos } from "./PagosPrestamos";
import { TipoTransaccion } from "./TipoTransaccion";
import { Usuario } from "./Usuario";

export class Transaccion{

    idTransaccion!: number;
    fechaTransaccion?: Date;
    montoTotal?: number;
    totalTransacciones?: number

    tipoTransaccionId?: number;
    nombreTipoTransaccion?: string;

    usuarioId?: number;
    identificacion?: number;
    nombreUsuario?: string;
    apellidoPaterno?: string;

    estadoId?: number;
    nombreEstado?: string;

   // movimientosAhorroId: number;
   // montoAhorros: number;


   // pagosPrestamosId: number;
   // montoAbonadoPrestamos: number;
    

}