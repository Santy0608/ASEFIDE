import { Component, OnInit } from '@angular/core';
import { MovimientosAhorro } from '../../domain/MovimientosAhorro';
import { MovimientosAhorroService } from '../../services/movimientos-ahorro.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceMovimiento } from '../../sharing-data-service/sharing-data-service-movimientos-ahorro';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-movimientos-ahorro',
  imports: [RouterModule, CommonModule, RouterLink, PaginatorComponent
  ],
  templateUrl: './movimientos-ahorro.component.html',
})
export class MovimientosAhorroComponent implements OnInit{

  movimientosAhorro: MovimientosAhorro[] = [];
  errors: any;
  paginator: any = {};

  constructor(private movimientosAhorroService: MovimientosAhorroService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceMovimiento){

  }

  ngOnInit(): void {
    this.listadoMovimientosAhorrosCompletos();
  }

   listadoMovimientosAhorro(): void{
      this.route.paramMap.pipe(
        switchMap(params => {
        const page = +(params.get('page') || '0');
          return this.movimientosAhorroService.listadoMovimientosAhorroPaginacion(page);
        })
        ).subscribe(pageable => {
          this.movimientosAhorro = pageable.content as MovimientosAhorro[];
          this.paginator = pageable;
        });
    }

    listadoMovimientosAhorrosCompletos(): void{
      this.movimientosAhorroService.listadoMovimientosAhorrosCompletos().subscribe(
        data => {
          this.movimientosAhorro = data;
        }
      )
    }
                 
    pageMovimientosAhorroEvent(): void{
      this.sharingDataService.pageMovimientosAhorroEventEmitter.subscribe(pageable => {
        this.movimientosAhorro = pageable.movimientosAhorro;
        this.paginator = pageable.paginator;
      })
    }

  OnSelectedMovimientoAhorro(movimientoAhorro: MovimientosAhorro){
    this.router.navigate(['/app/movimientos-ahorro/editar-movimiento-ahorro', movimientoAhorro.idMovimiento]);
  }

}
