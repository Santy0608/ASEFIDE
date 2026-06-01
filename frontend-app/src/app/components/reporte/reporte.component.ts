import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Reporte } from '../../domain/Reporte';
import { ReporteService } from '../../services/reporte.service';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceReporte } from '../../sharing-data-service/sharing-data-service-reporte';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reporte',
  imports: [RouterModule, CommonModule, PaginatorComponent, FormsModule],
  templateUrl: './reporte.component.html',
})
export class ReporteComponent implements OnInit{

  reportes: Reporte[] = [];
  errors: any;
  paginator: any = {};

  fechaInicio: string = '';
  fechaFinal: string = '';
  loading: boolean = false;

  constructor(private reporteService: ReporteService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceReporte){

  }

  ngOnInit(): void {
    this.listadoReportesCompletos();
  }



  listadoReportes(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.reporteService.listadoReportePaginacion(page);
      })
      ).subscribe(pageable => {
        this.reportes = pageable.content as Reporte[];
        this.paginator = pageable;
      });
  }

  listadoReportesCompletos(): void{
    this.reporteService.listadoReportesCompletos().subscribe(
      data => {
        this.reportes = data;
      }
    )
  }
                         
  pageReportesEvent(): void{
    this.sharingDataService.pageReporteEventEmitter.subscribe(pageable => {
      this.reportes = pageable.reportes;
      this.paginator = pageable.paginator;
    })
  }

  //OnSelectedReporte(reporte: Reporte): void {
  //  this.router.navigate(['/app/reportes/editar-reporte', reporte.idReporte]);
  //}
    
  eliminarReporte(idReporte: number): void {
    Swal.fire({
      title: "¿Eliminar reporte?",
      text: "Esta acción no se puede revertir.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Sí, eliminar",
      cancelButtonText: "Cancelar"
    }).then((result) => {
      if (result.isConfirmed) {
        this.reporteService.eliminarReporte(idReporte).subscribe({
          next: () => {
            Swal.fire("Eliminado", "El reporte fue eliminado correctamente", "success");
            this.listadoReportes(); // Refresca la lista
          },
          error: (err) => {
            console.error(err);
            Swal.fire("Error", "No se pudo eliminar el reporte", "error");
          }
        });
      }
    });
  }

  descargarPDF(): void {
    if (!this.fechaInicio || !this.fechaFinal) {
      alert('Seleccione un rango de fechas');
      return;
    }

    this.reporteService.descargarReportePDF(this.fechaInicio, this.fechaFinal)
      .subscribe(res => {
        const url = window.URL.createObjectURL(res);
        window.open(url); // abre PDF en nueva pestaña
      }, error => {
        console.error('Error generando PDF', error);
      });
  }

}
