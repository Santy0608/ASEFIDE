import { Component, OnInit } from '@angular/core';
import { TipoReporte } from '../../domain/TipoReporte';
import { TipoReporteService } from '../../services/tipo-reporte.service';
import { SharingDataServiceTipoReporte } from '../../sharing-data-service/sharing-data-service-tipo-reporte';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tipo-reporte-form',
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './tipo-reporte-form.component.html',
})
export class TipoReporteFormComponent implements OnInit{

  errors: any;
  tipoReporte!: TipoReporte;

  constructor(private tipoReporteService: TipoReporteService,
              private sharingDataService: SharingDataServiceTipoReporte,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.tipoReporte = new TipoReporte();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsTipoReporteFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectTipoReporteEventEmitter.subscribe(tipoReporte => this.tipoReporte = tipoReporte);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idTipoReporte') || '0');
      if (id > 0){
        this.tipoReporteService.buscarTipoReportePorId(id).subscribe(tipoReporte => this.tipoReporte = tipoReporte);
      }
    })
  }

  onSubmit(tipoReporteForm: NgForm): void {

  if (tipoReporteForm.invalid) return;

  console.log("Datos enviados:", this.tipoReporte);

  if (this.tipoReporte.idTipoReporte > 0) {

    this.tipoReporteService.editarTipoReporte(this.tipoReporte).subscribe({
      next: () => {
        Swal.fire({
          title: "Actualizado",
          text: "Tipo Reporte actualizado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/tipos-reportes']);
        });
      },
      error: (err: any) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  } else {

    this.tipoReporteService.guardarTipoReporte(this.tipoReporte).subscribe({
      next: () => {
        Swal.fire({
          title: "Creado",
          text: "Tipo Reporte creado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/tipos-reportes']);
        });
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  }

}
}
