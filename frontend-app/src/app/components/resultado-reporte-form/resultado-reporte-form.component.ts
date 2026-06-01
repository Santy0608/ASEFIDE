import { Component, OnInit } from '@angular/core';
import { ResultadoReporte } from '../../domain/ResultadosReporte';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { SharingDataServiceResultadoReporte } from '../../sharing-data-service/sharing-data-service-resultado-reporte';
import { ResultadoReporteService } from '../../services/resultado-reporte.service';
import Swal from 'sweetalert2';
import { ReporteService } from '../../services/reporte.service';
import { Reporte } from '../../domain/Reporte';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-resultado-reporte-form',
  imports: [CommonModule, RouterModule, FormsModule, NgSelectModule],
  templateUrl: './resultado-reporte-form.component.html',
})
export class ResultadoReporteFormComponent implements OnInit{

  resultadoReporte!: ResultadoReporte;
  reportes: Reporte[] = [];
  errors: any;

  constructor(private sharingDataService: SharingDataServiceResultadoReporte,
              private resultadoReporteService: ResultadoReporteService,
              private router: Router,
              private route: ActivatedRoute,
              private reporteService: ReporteService
  ){
    this.resultadoReporte = new ResultadoReporte();
  }



  ngOnInit(): void {
    this.sharingDataService.errorsResultadoReporteFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectResultadoReporteEventEmitter.subscribe(resultadoReporte => this.resultadoReporte = resultadoReporte);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idResultado') || '0');
      if (id > 0){
        this.resultadoReporteService.buscarResultadoReportePorId(id).subscribe(resultadoReporte => this.resultadoReporte = resultadoReporte);
      }
    })
    this.cargarReportes();
  }

  onSubmit(resultadoReporteForm: NgForm): void {
      if (resultadoReporteForm.invalid) return;
  
      const resultaReporteToSend = {
        idResultado: this.resultadoReporte.idResultado,
        metricaNombre: this.resultadoReporte.metricaNombre,
        metricaValor: this.resultadoReporte.metricaValor,
        
        reporteId: Number(this.resultadoReporte.reporteId)
      };
  
      const request$ = this.resultadoReporte.idResultado > 0
        ? this.resultadoReporteService.editarResultadoReporte(resultaReporteToSend)
        : this.resultadoReporteService.guardarResultadoReporte(resultaReporteToSend);
  
      request$.subscribe({
        next: () => {
          Swal.fire("Éxito", "Reporte gestionado correctamente", "success");
          this.router.navigate(['/app/reportes']);
        },
        error: (err) => {
          this.sharingDataService.errorsResultadoReporteFormEventEmitter.emit(err);
        }
      });
    }

  cargarReportes(): void {
    this.reporteService.listadoReportes().subscribe(
      data => {
        this.reportes = data;
      }
    )
  } 

}
