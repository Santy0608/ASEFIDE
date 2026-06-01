import { Component, OnInit } from '@angular/core';
import { MovimientosAhorro } from '../../domain/MovimientosAhorro';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServiceMovimiento } from '../../sharing-data-service/sharing-data-service-movimientos-ahorro';
import { MovimientosAhorroService } from '../../services/movimientos-ahorro.service';
import { FormsModule, NgForm } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { AhorroService } from '../../services/ahorro.service';
import { TransaccionService } from '../../services/transaccion.service';

@Component({
  selector: 'app-movimientos-ahorro-form',
  imports: [FormsModule, NgSelectModule, CommonModule, RouterModule],
  templateUrl: './movimientos-ahorro-form.component.html',
})
export class MovimientosAhorroFormComponent implements OnInit {

  errors: any;
  movimiento: MovimientosAhorro;
  
  ahorros: any[] = [];
  transacciones: any[] = [];
  transaccionesFiltradas: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sharingDataService: SharingDataServiceMovimiento,
    private movimientoService: MovimientosAhorroService,
    private ahorroService: AhorroService,
    private transaccionService: TransaccionService
  ) {
    this.movimiento = new MovimientosAhorro();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsMovimientoFormEventEmitter.subscribe(err => this.errors = err);
    this.sharingDataService.selectMovimientoEventEmitter.subscribe(data => this.movimiento = data);

    this.route.paramMap.subscribe(params => {
      const id = +(params.get('idMovimiento') || '0');
      if (id > 0) {
        this.movimientoService.buscarMovimientoAhorroPorId(id).subscribe(data => this.movimiento = data);
      }
    });
    this.cargarCatalogos();
  }

  onSubmit(form: NgForm): void {
    if (form.invalid) return;

    // Aplanamiento de datos para el DTO de backend
    const movimientoToSend = {
      idMovimiento: this.movimiento.idMovimiento,
      cuentasAhorroId: Number(this.movimiento.cuentasAhorroId),
      transaccionId: Number(this.movimiento.transaccionId),
      monto: Number(this.movimiento.monto),
      fechaDeposito: this.movimiento.fechaDeposito,
      tipoMovimiento: this.movimiento.tipoMovimiento
    };

    const request$ = this.movimiento.idMovimiento > 0
      ? this.movimientoService.editarMovimientoAhorro(movimientoToSend)
      : this.movimientoService.guardarMovimientoAhorro(movimientoToSend);

    request$.subscribe({
      next: () => {
        Swal.fire("Éxito", "Movimiento de ahorro procesado", "success");
        this.router.navigate(['/app/movimientos-ahorro']);
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
        this.sharingDataService.errorsMovimientoFormEventEmitter.emit(err);
      }
    });
  }

  cargarCatalogos(): void {
    // Carga de transacciones
    this.transaccionService.listadoTransaccionesCompletos().subscribe(data => {
      console.log('Data transacciones raw:', data); 
      this.transacciones = data.map(t => ({
        ...t,
        descripcion: `ID: ${t.idTransaccion} - Ref: ${t.montoTotal || 'N/A'}`
      }));
    });

    // Carga de ahorros
    this.ahorroService.listadoCuentasAhorrosCompletos().subscribe(data => {
      console.log('Data ahorros raw:', data); 
      this.ahorros = data.map(a => ({
        ...a,
        descripcion: `Cuenta #${a.idAhorro} - Titular: ${a.nombreTipoAhorro}`
      }));
    });
}

  tiposMovimiento = [
      { nombre: 'Depósito', valor: 'DEPOSITO' },
      { nombre: 'Retiro', valor: 'RETIRO' }
  ];

  onCuentaAhorroChange(idAhorro: number): void {
    if (!idAhorro) return;
    
    const cuentaSeleccionada = this.ahorros.find(a => a.idAhorro === idAhorro);
    if (cuentaSeleccionada) {
        // Filtrar transacciones por usuario
        this.transaccionesFiltradas = this.transacciones.filter(
            t => t.usuarioId  === cuentaSeleccionada.usuarioId
        );
        console.log('Cuenta seleccionada:', cuentaSeleccionada)
    }
}

}
