import { Transaccion } from "./Transaccion";

export class DetalleTransaccion{
    idDetalle!: number;
    concepto?: string;
    fechaTransaccion?: Date;
    subTotal?: number;
    transaccionId?: number;
    montoTotalTransaccion?: number;
}