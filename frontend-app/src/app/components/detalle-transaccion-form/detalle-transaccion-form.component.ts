import { Component, OnInit } from '@angular/core';
import { DetalleTransaccion } from '../../domain/DetallesTransaccion';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServiceDetalle } from '../../sharing-data-service/sharing-data-service-detalle-transaccion';
import { DetalleTransaccionService } from '../../services/detalle-transaccion.service';
import { TransaccionService } from '../../services/transaccion.service';
import { Transaccion } from '../../domain/Transaccion';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-detalle-transaccion-form',
  imports: [CommonModule, NgSelectModule, RouterModule, FormsModule],
  templateUrl: './detalle-transaccion-form.component.html',
})
export class DetalleFormComponent implements OnInit {

  errors: any;
  detalle: DetalleTransaccion;
  transacciones: Transaccion[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sharingDataService: SharingDataServiceDetalle,
    private detalleService: DetalleTransaccionService,
    private transaccionService: TransaccionService
  ) {
    this.detalle = new DetalleTransaccion();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsDetalleFormEventEmitter.subscribe(err => this.errors = err);
    this.sharingDataService.selectDetalleEventEmitter.subscribe(data => this.detalle = data);

    this.route.paramMap.subscribe(params => {
      const id = +(params.get('idDetalle') || '0');
      if (id > 0) {
        // Asumiendo que tienes un servicio de búsqueda por ID
        this.detalleService.buscarDetalleTransaccionPorId(id).subscribe(data => this.detalle = data);
      }
    });
    this.cargarTransacciones();
  }

  onSubmit(form: NgForm): void {
    if (form.invalid) return;

    // Preparamos el objeto para el backend
    const detalleToSend = {
      idDetalle: this.detalle.idDetalle,
      concepto: this.detalle.concepto,

      subTotal: Number(this.detalle.subTotal),
      transaccionId: Number(this.detalle.transaccionId)
    };

    const request$ = this.detalle.idDetalle > 0
      ? this.detalleService.editarDetalleTransaccion(detalleToSend)
      : this.detalleService.guardarDetalleTransaccion(detalleToSend);

    request$.subscribe({
      next: () => {
        Swal.fire("Éxito", "Detalle guardado correctamente", "success");
        this.router.navigate(['/app/detalle-transaccion']);
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
        this.sharingDataService.errorsDetalleFormEventEmitter.emit(err);
      }
    });
  }

  cargarTransacciones(): void {
    this.transaccionService.listadoTransaccionesCompletos().subscribe(data => {
      this.transacciones = data.map(t => ({
        ...t,
        descripcionResumida: `Transacción #${t.idTransaccion} - Fecha: ${t.fechaTransaccion}`
      }));
    });
  }
}