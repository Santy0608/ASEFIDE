export class Estado{

    idEstado!:number;
    nombre?:string; 
    cantidadUsuarios!: number;
    prestamoId?: number;
    montoSolicitado?: number;
    saldoPendiente?: number;
    tasaIntereses?: number;
    plazoMeses?: number;
    fechaAprobacion?: number;
    estadoPrestamo?: string;
    cliente?: string;

    cantidadPrestamos!: number;
}