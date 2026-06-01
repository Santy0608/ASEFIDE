import { Component, OnInit } from '@angular/core';
import { PagosPrestamos } from '../../domain/PagosPrestamos';
import { Transaccion } from '../../domain/Transaccion';
import { Prestamo } from '../../domain/Prestamo';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServicePago } from '../../sharing-data-service/sharing-data-service-pago-prestamo';
import { PagosPrestamosService } from '../../services/pagos-prestamo.service';
import { FormsModule, NgForm } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { TransaccionService } from '../../services/transaccion.service';
import { PrestamoService } from '../../services/prestamo.service';

@Component({
  selector: 'app-pagos-prestamos-form',
  imports: [FormsModule, NgSelectModule, CommonModule, RouterModule],
  templateUrl: './pagos-prestamos-form.component.html'
})
export class PagoFormComponent implements OnInit {

  errors: any;
  pago: PagosPrestamos;
  
  // Listas para los selects
  prestamos: Prestamo[] = [];
  transacciones: Transaccion[] = [];
  transaccionesFiltradas: Transaccion[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sharingDataService: SharingDataServicePago,
    private pagoService: PagosPrestamosService,
    private transaccionService: TransaccionService,
    private prestamoService: PrestamoService
  ) {
    this.pago = new PagosPrestamos();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsPagoFormEventEmitter.subscribe(err => this.errors = err);
    this.sharingDataService.selectPagoEventEmitter.subscribe(data => this.pago = data);

    this.route.paramMap.subscribe(params => {
      const id = +(params.get('idPago') || '0');
      if (id > 0) {
        this.pagoService.buscarPagosPrestamosPorId(id).subscribe(data => this.pago = data);
      }
    });

    this.cargarTransacciones();
    this.cargarPrestamos();
  }

  onSubmit(form: NgForm): void {
    if (form.invalid) return;

    // Mapeo preciso al DTO del backend
    const pagoToSend = {
      idPago: this.pago.idPago,
      transaccionId: Number(this.pago.transaccionId),
      prestamoId: Number(this.pago.prestamoId),
      montoAbonado: Number(this.pago.montoAbonado),
      fechaPago: this.pago.fechaPago
    };

    console.log('Pago a enviar:', {
        idTransaccion: this.pago.transaccionId,
        idPrestamo: this.pago.prestamoId,
        montoAbonado: this.pago.montoAbonado,
        fechaPago: this.pago.fechaPago
    });

    const request$ = this.pago.idPago > 0
      ? this.pagoService.editarPagosPrestamos(pagoToSend)
      : this.pagoService.guardarPagoPrestamo(pagoToSend);

    request$.subscribe({
      next: () => {
        Swal.fire("Éxito", "Pago registrado correctamente", "success");
        this.router.navigate(['/app/pagos-prestamos']);
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
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

  cargarPrestamos(): void {
  this.prestamoService.listadoPrestamosCompletos().subscribe(data => {
    this.prestamos = data.map(p => ({
      ...p,
      // Esta propiedad es la que el usuario verá en el dropdown
      descripcionResumida: `Préstamo #${p.idPrestamo} - Usuario: ${p.nombreUsuario} - Saldo: $${p.saldoPendiente}`
    }));
  });
}

  onPrestamoChange(idPrestamo: number | undefined): void {
    if (!idPrestamo) return;
    
    const prestamoSeleccionado = this.prestamos.find(p => p.idPrestamo === idPrestamo);
    if (prestamoSeleccionado) {
        this.transaccionesFiltradas = this.transacciones.filter(
            t => t.usuarioId === prestamoSeleccionado.usuarioId
        );
        console.log('Prestamo seleccionado:', prestamoSeleccionado);
        console.log('Transacciones filtradas:', this.transaccionesFiltradas);
    }
  }

}

