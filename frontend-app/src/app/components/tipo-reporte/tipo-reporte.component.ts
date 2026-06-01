import { Component, OnInit } from '@angular/core';
import { TipoReporte } from '../../domain/TipoReporte';
import { TipoReporteService } from '../../services/tipo-reporte.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { SharingDataServiceTipoReporte } from '../../sharing-data-service/sharing-data-service-tipo-reporte';
import { switchMap } from 'rxjs';
import { PaginatorComponent } from '../paginator/paginator.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tipo-reporte',
  imports: [CommonModule, RouterModule, PaginatorComponent, FormsModule],
  templateUrl: './tipo-reporte.component.html',
})
export class TipoReporteComponent implements OnInit{

  tiposReportes: TipoReporte[] = [];
  errors: any;
  paginator: any = {};
  nombreTipoReporteBuscar: string = '';
  estadoBuscando: boolean = false;

  constructor(private tipoReporteService: TipoReporteService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceTipoReporte){

  }

  ngOnInit(): void {
    this.listadoTiposReportesCompletos();
  }

  listadoTipoReportes(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.tipoReporteService.listadoTipoReportePaginacion(page);
      })
      ).subscribe(pageable => {
        this.tiposReportes = pageable.content as TipoReporte[];
        this.paginator = pageable;
      });
  }

  listadoTiposReportesCompletos(): void{
    this.tipoReporteService.listadoTiposReportesCompletos().subscribe(
      data => {
        this.tiposReportes = data;
      }
    )
  }
          
  pageTipoReportesEvent(): void{
    this.sharingDataService.pageTipoReporteEventEmitter.subscribe(pageable => {
      this.tiposReportes = pageable.tiposAhorros;
      this.paginator = pageable.paginator;
    })
  }

  OnSelectedTipoReporte(tipoReporte: TipoReporte): void{
    this.router.navigate(['/app/tipos-reportes/editar-tipo-reporte', tipoReporte.idTipoReporte])
  }
    
  buscarTipoReporte(): void{
    if (this.nombreTipoReporteBuscar.trim() === ''){
      this.listadoTipoReportes();
      this.estadoBuscando = false;
      return;
    }
        
    this.tipoReporteService.buscarTipoReportePorNombre(this.nombreTipoReporteBuscar).subscribe({
      next: (resultado) => {
        this.tiposReportes = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreTipoReporteBuscar = '';
    this.listadoTipoReportes();
  }
  

  eliminarTipoReporte(idTipoReporte: number): void {
    
        Swal.fire({
          title: "¿Eliminar tipo reporte?",
          text: "El tipo de reporte se marcará como inactivo.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#d33",
          cancelButtonColor: "#3085d6",
          confirmButtonText: "Sí, eliminar",
          cancelButtonText: "Cancelar"
        }).then((result) => {
    
          if (result.isConfirmed) {
    
            this.tipoReporteService.eliminarTipoReporte(idTipoReporte).subscribe({
    
              next: () => {
    
                Swal.fire(
                  "Eliminado",
                  "El tipo de reporte fue eliminado correctamente",
                  "success"
                );
    
                // quitar del listado sin recargar
               this.listadoTipoReportes();
    
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
