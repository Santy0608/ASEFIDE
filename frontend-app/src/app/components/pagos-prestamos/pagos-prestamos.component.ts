import { Component, OnInit } from '@angular/core';
import { PagosPrestamos } from '../../domain/PagosPrestamos';
import { PagosPrestamosService } from '../../services/pagos-prestamo.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServicePago } from '../../sharing-data-service/sharing-data-service-pago-prestamo';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-pagos-prestamos',
  imports: [RouterModule, CommonModule, RouterLink, PaginatorComponent],
  templateUrl: './pagos-prestamos.component.html',
})
export class PagosPrestamosComponent implements OnInit{

  pagosPrestamos: PagosPrestamos[] = [];
  paginator: any = {};
  errors: any;

  constructor(private pagosPrestamoService: PagosPrestamosService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServicePago){

  }

  ngOnInit(): void {
    this.listadoPagosPrestamosCompletos();
  }

  listadoPagosPrestamos(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.pagosPrestamoService.listadoPagosPaginacion(page);
      })
      ).subscribe(pageable => {
        this.pagosPrestamos = pageable.content as PagosPrestamos[];
        this.paginator = pageable;
      });
  }

  listadoPagosPrestamosCompletos(): void{
    this.pagosPrestamoService.listadoPagosPrestamosCompletos().subscribe(
      data => {
        this.pagosPrestamos = data;
      }
    )
  }
                   
  pagePagosPrestamosEvent(): void{
    this.sharingDataService.pagePagoEventEmitter.subscribe(pageable => {
      this.pagosPrestamos = pageable.pagosPrestamos;
      this.paginator = pageable.paginator;
    })
  }

  OnSelectedPagoPrestamo(pagoPrestamo: PagosPrestamos){
    this.router.navigate(['/app/pagos-prestamos/editar-pago-prestamo', pagoPrestamo.idPago]);
  }


}
