export interface PrestamoUsuario{

    nombreUsuario: string;
    montoSolicitado: number;
    saldoPendiente: number;
    cuotasPagadas: number;
    proximaFechaPago: Date;
    estado: string;

}