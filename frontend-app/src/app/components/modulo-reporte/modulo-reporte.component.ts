import { Component, OnInit } from '@angular/core';
import { ModuloReporte } from '../../domain/ModuloReporte';
import { ModuloReporteService } from '../../services/modulo-reporte.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceModuloReporte } from '../../sharing-data-service/sharing-data-service-modulo-reporte';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { D } from '@angular/cdk/keycodes';

@Component({
  selector: 'app-modulo-reporte',
  imports: [RouterModule, CommonModule, PaginatorComponent, FormsModule],
  templateUrl: './modulo-reporte.component.html',
  styleUrl: './modulo-reporte.component.css'
})
export class ModuloReporteComponent implements OnInit{

  moduloReportes: ModuloReporte[] = [];
  errors: any;
  paginator: any = {};
  nombreModuloReporteBuscar: string = '';
  estadoBuscando: boolean = false;

  constructor(private moduloReporteService: ModuloReporteService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceModuloReporte){

  }

  ngOnInit(): void {
    this.listadoModulosReportesCompletos();
  }

  listadoModuloReportes(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.moduloReporteService.listadoModuloReportePaginacion(page);
      })
      ).subscribe(pageable => {
        this.moduloReportes = pageable.content as ModuloReporte[];
        this.paginator = pageable;
      });
  }

  listadoModulosReportesCompletos(): void{
    this.moduloReporteService.listadoModulosReportesCompletos().subscribe(
      data => {
        this.moduloReportes = data;
      }
    )
  }
               
    pageModuloReportesEvent(): void{
      this.sharingDataService.pageModuloReporteEventEmitter.subscribe(pageable => {
        this.moduloReportes = pageable.moduloReportes;
        this.paginator = pageable.paginator;
      })
    }

  OnSelectedModuloReporte(moduloReporte: ModuloReporte): void{
    this.router.navigate(['/app/modulo-reporte/editar-modulo-reporte', moduloReporte.idModulo])
  }
    
  buscarModuloReporte(): void{
    if (this.nombreModuloReporteBuscar.trim() === ''){
      this.listadoModuloReportes();
      this.estadoBuscando = false;
      return;
    }
        
    this.moduloReporteService.buscarModuloReportePorNombre(this.nombreModuloReporteBuscar).subscribe({
      next: (resultado) => {
        this.moduloReportes = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreModuloReporteBuscar = '';
    this.listadoModuloReportes();
  }
    
  eliminarModuloReporte(idModulo: number): void {
    
        Swal.fire({
          title: "¿Eliminar modulo reporte?",
          text: "El modulo reporte se marcará como inactivo.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#d33",
          cancelButtonColor: "#3085d6",
          confirmButtonText: "Sí, eliminar",
          cancelButtonText: "Cancelar"
        }).then((result) => {
    
          if (result.isConfirmed) {
    
            this.moduloReporteService.eliminarModuloReporte(idModulo).subscribe({
    
              next: () => {
    
                Swal.fire(
                  "Eliminado",
                  "El modulo reporte fue eliminado correctamente",
                  "success"
                );
    
                // quitar del listado sin recargar
               this.listadoModuloReportes();
    
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
