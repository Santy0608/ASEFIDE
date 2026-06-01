import { Component, OnInit } from '@angular/core';
import { ResultadoReporte } from '../../domain/ResultadosReporte';
import { ResultadoReporteService } from '../../services/resultado-reporte.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceResultadoReporte } from '../../sharing-data-service/sharing-data-service-resultado-reporte';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-resultado-reporte',
  imports: [RouterModule, CommonModule, RouterLink, PaginatorComponent],
  templateUrl: './resultado-reporte.component.html',
})
export class ResultadoReporteComponent implements OnInit{

  resultadosReportes: ResultadoReporte[] = [];
  errors: any;
  paginator: any = {};

  constructor(private resultadoReporteService: ResultadoReporteService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceResultadoReporte){
    
  }

  ngOnInit(): void {
    this.listadoResultadosReportesCompletos();
  }

  listadoResultadoReportes(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.resultadoReporteService.listadoResultadosReportePaginacion(page);
      })
      ).subscribe(pageable => {
        this.resultadosReportes = pageable.content as ResultadoReporte[];
        this.paginator = pageable;
      });
  }

  listadoResultadosReportesCompletos(): void{
    this.resultadoReporteService.listadoResultadosReportesCompletos().subscribe(
      data => {
        this.resultadosReportes = data;
      }
    )
  }
                           
    pageResultadoReportesEvent(): void{
      this.sharingDataService.pageResultadosReporteEventEmitter.subscribe(pageable => {
        this.resultadosReportes = pageable.resultadosReportes;
        this.paginator = pageable.paginator;
      })
    }

  OnSelectedResultadoReporte(reporte: ResultadoReporte): void{
    this.router.navigate(['/app/resultados-reporte/editar-resultado-reporte', reporte.idResultado])
  }

  

}
