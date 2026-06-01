import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { DetalleTransaccion } from '../../domain/DetallesTransaccion';
import { DetalleTransaccionService } from '../../services/detalle-transaccion.service';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceTransaccion } from '../../sharing-data-service/sharing-data-service-transaccion';
import { switchMap } from 'rxjs';
import { SharingDataServiceDetalle } from '../../sharing-data-service/sharing-data-service-detalle-transaccion';

@Component({
  selector: 'app-detalle-transaccion',
  imports: [CommonModule, RouterModule, PaginatorComponent],
  templateUrl: './detalle-transaccion.component.html',
})
export class DetalleTransaccionComponent implements OnInit{

  detallesTransacciones: DetalleTransaccion[] = [];
  errors: any;
  paginator: any = {};

  constructor(private detalleTransaccionService: DetalleTransaccionService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceDetalle){

  }

  ngOnInit(): void {
    this.listadoDetallesTransaccionesCompletas();
  }

  listadoDetallesTransacciones(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.detalleTransaccionService.listadoDetalleTransaccionPaginacion(page);
      })
      ).subscribe(pageable => {
        this.detallesTransacciones = pageable.content as DetalleTransaccion[];
        this.paginator = pageable;
    });
  }

  listadoDetallesTransaccionesCompletas(): void{
    this.detalleTransaccionService.listadoDetallesTransaccionesCompletas().subscribe(
      data => {
        this.detallesTransacciones = data;
      }
    )
  }
         
    pageDetallesTransaccionesEvent(): void{
       this.sharingDataService.pageDetallesTransaccionesEventEmitter.subscribe(pageable => {
         this.detallesTransacciones = pageable.ahorros;
         this.paginator = pageable.paginator;
       })
     }

  OnSelectedDetalleTransaccion(detalleTransaccion: DetalleTransaccion){
    this.router.navigate(['/app/detalle-transaccion/editar-detalle-transaccion', detalleTransaccion.idDetalle]);
  }



}
