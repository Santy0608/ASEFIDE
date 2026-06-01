import { Component, OnInit } from '@angular/core';
import { Prestamo } from '../../domain/Prestamo';
import { PrestamoService } from '../../services/prestamo.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { SharingDataServicePrestamo } from '../../sharing-data-service/sharing-data-service-prestamo';
import { PaginatorComponent } from '../paginator/paginator.component';
import { switchMap } from 'rxjs';
import { Estado } from '../../domain/Estado';

@Component({
  selector: 'app-prestamo',
  imports: [RouterModule, CommonModule, RouterLink, PaginatorComponent],
  templateUrl: './prestamo.component.html',
})
export class PrestamoComponent implements OnInit{

  prestamos: Prestamo[] = [];
  errors: any;
  paginator: any = {};
  estadoPrestamos: Estado[] = [];
  totalPrestado: number = 0;
  totalSaldoPendiente: number = 0;
  totalActivos: number = 0;
  totalInactivos: number = 0;

  constructor(private prestamoService: PrestamoService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServicePrestamo){

  }



  ngOnInit(): void {
    this.listadoPrestamosCompletos();
    this.cargarEstados();
    this.cargarVistaPrestamos();
  }

  cargarVistaPrestamos(): void {
    this.prestamoService.obtenerEstadoPrestamos().subscribe({
      next: (data) => {
        this.estadoPrestamos = data ?? [];
        this.calcularMetricas();
      },
      error: (err) => console.error(err)
    });
  }

  cargarEstados(): void{
    this.prestamoService.prestamosPorEstado().subscribe({
      next: (data) => {
        data.forEach(e => {
          if (e.nombre === 'ACTIVO')   this.totalActivos   = e.cantidadPrestamos;
          if (e.nombre === 'INACTIVO') this.totalInactivos = e.cantidadPrestamos;
        });
      },
      error: (err) => console.error(err)
    })
  }

  calcularMetricas(): void {
    this.totalPrestado = this.estadoPrestamos
      .reduce((acc, p) => acc + (p.montoSolicitado ?? 0), 0);

    this.totalSaldoPendiente = this.estadoPrestamos
      .reduce((acc, p) => acc + (p.saldoPendiente ?? 0), 0);

    this.totalActivos = this.estadoPrestamos
      .filter(p => p.estadoPrestamo === 'Completado').length;
  }

  listadoPrestamos(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.prestamoService.listadoPrestamosPaginacion(page);
      })
      ).subscribe(pageable => {
        this.prestamos = pageable.content as Prestamo[];
        this.paginator = pageable;
      });
  }

  listadoPrestamosCompletos(): void{
    this.prestamoService.listadoPrestamosCompletos().subscribe(
      data => {
        this.prestamos = data;
      }
    )
  }
                     
    pagePagosPrestamosEvent(): void{
      this.sharingDataService.pagePrestamoEventEmitter.subscribe(pageable => {
        this.prestamos = pageable.prestamos;
        this.paginator = pageable.paginator;
      })
    }

  OnSelectedPrestamo(prestamo: Prestamo): void{
    this.router.navigate(['/app/prestamos/editar-prestamo', prestamo.idPrestamo])
  }
        
        
    eliminarPrestamo(idPrestamo: number): void {
        
            Swal.fire({
              title: "¿Eliminar prestamo?",
              text: "El prestamo se marcará como inactivo.",
              icon: "warning",
              showCancelButton: true,
              confirmButtonColor: "#d33",
              cancelButtonColor: "#3085d6",
              confirmButtonText: "Sí, eliminar",
              cancelButtonText: "Cancelar"
            }).then((result) => {
        
              if (result.isConfirmed) {
        
                this.prestamoService.eliminarPrestamo(idPrestamo).subscribe({
        
                  next: () => {
        
                    Swal.fire(
                      "Eliminado",
                      "El prestamo fue eliminado correctamente",
                      "success"
                    );
        
                   this.listadoPrestamos();
        
                  },
        
                  error: (err) => {
                    const mensaje = err.error?.mensaje || 'Error inesperado';
                    Swal.fire("Error", mensaje, "error");
                  }
        
                });
        
              }
        
            });
        
          }

}
