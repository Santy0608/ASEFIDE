import { Component, OnInit } from '@angular/core';
import { ModuloReporte } from '../../domain/ModuloReporte';
import { ModuloReporteService } from '../../services/modulo-reporte.service';
import { SharingDataServiceModuloReporte } from '../../sharing-data-service/sharing-data-service-modulo-reporte';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modulo-reporte-form',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './modulo-reporte-form.component.html',
})
export class ModuloReporteFormComponent implements OnInit{

  errors: any;
  moduloReporte!: ModuloReporte;

  constructor(private moduloReporteService: ModuloReporteService,
              private sharingDataService: SharingDataServiceModuloReporte,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.moduloReporte = new ModuloReporte();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsModuloReporteFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectModuloReporteEventEmitter.subscribe(moduloReporte => this.moduloReporte = moduloReporte);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idModulo') || '0');
      if (id > 0){
        this.moduloReporteService.buscarModuloReportePorId(id).subscribe(moduloReporte => this.moduloReporte = moduloReporte);
      }
    })
  }

  onSubmit(moduloReporteForm: NgForm): void {

  if (moduloReporteForm.invalid) return;

  console.log("Datos enviados:", this.moduloReporte);

  if (this.moduloReporte.idModulo > 0) {

    this.moduloReporteService.editarModuloReporte(this.moduloReporte).subscribe({
      next: () => {
        Swal.fire({
          title: "Actualizado",
          text: "Modulo Reporte actualizado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/modulo-reporte']);
        });
      },
      error: (err) => {
        console.error(err);
      }
    });

  } else {

    this.moduloReporteService.guardarModuloReporte(this.moduloReporte).subscribe({
      next: () => {
        Swal.fire({
          title: "Creado",
          text: "Modulo Reporte creado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/modulo-reporte']);
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
