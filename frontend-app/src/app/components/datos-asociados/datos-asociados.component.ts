import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { DatosAsociados } from '../../domain/DatosAsociados';
import { DatosAsociadosService } from '../../services/datos-asociados.service';
import { switchMap } from 'rxjs';
import { SharingDataServiceDatosAsociados } from '../../sharing-data-service/sharing-data-service-datos-asociados';
import { PaginatorComponent } from '../paginator/paginator.component';
import { AporteUsuario } from '../../domain/AporteUsuario';
import { Aporte } from '../../domain/Aporte';

@Component({
  selector: 'app-datos-asociados',
  imports: [RouterModule, CommonModule, PaginatorComponent],
  templateUrl: './datos-asociados.component.html',
})
export class DatosAsociadosComponent implements OnInit{

  datosAsociados: DatosAsociados[] = [];
  errors: any;
  aportes: Aporte[] = [];


  paginator: any = {};


  constructor(private datosAsociadosService: DatosAsociadosService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceDatosAsociados){

  }

  getAporteVigente(aportes: AporteUsuario[]): number {
    if (!aportes || aportes.length === 0) return 0;
    const vigente = aportes.find(a => !a.fechaFinal) ?? aportes[aportes.length - 1];
    return vigente?.monto ?? 0;
  }

  getTotalAportes(aportes: AporteUsuario[]): number {
    if (!aportes || aportes.length === 0) return 0;
    return aportes.reduce((acc, a) => acc + (a.monto ?? 0), 0);
  }

  ngOnInit(): void {
    this.listadoDatosAsociadosCompletos();
  }

  listadoDatosAsociados(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
      return this.datosAsociadosService.listadoDatosAsociadosPaginacion(page);
    })
    ).subscribe(pageable => {
      this.datosAsociados = pageable.content as DatosAsociados[];
      this.paginator = pageable;
    });
  }
  
  listadoDatosAsociadosCompletos(): void{
    this.datosAsociadosService.listadoDatosAsociadosCompletos().subscribe(
      data => {
        this.datosAsociados = data;
      }
    )
  }
          
  pageDatosAsociadosEvent(): void{
    this.sharingDataService.pageDatosAsociadosEventEmitter.subscribe(pageable => {
      this.datosAsociados = pageable.datosAsociados;
      this.paginator = pageable.paginator;
    })
  }

  OnSelectedDatosAsociados(datosAsociados: DatosAsociados): void{
    this.router.navigate(['/app/datos-asociados/actualizar-dato-asociado', datosAsociados.idDatosAsociados]);
  }


}
