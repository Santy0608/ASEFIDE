import { Component } from '@angular/core';
import { Actividad } from '../../domain/Actividad';
import { ActividadService } from '../../services/actividad.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { SharingDataServiceActividad } from '../../sharing-data-service/sharing-data-service-actividad';
import { switchMap } from 'rxjs';
import { PaginatorComponent } from '../paginator/paginator.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-actividad',
  imports: [RouterModule, RouterLink, CommonModule, PaginatorComponent, FormsModule],
  templateUrl: './actividad.component.html',
})
export class ActividadComponent {

  actividades: Actividad[] = [];
  nombreBuscar: string = '';
  paginator: any = {};
  estadoBuscando: boolean = false;

  constructor(private actividadService: ActividadService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceActividad ){

  }

  ngOnInit(): void {
    this.listadoActividadesCompletas();
  }

  listadoActividades(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
        const page = +(params.get('page') || '0');
        return this.actividadService.listadoActividadesPaginacion(page);
      })
      ).subscribe(pageable => {
        this.actividades = pageable.content as Actividad[];
        this.paginator = pageable;
      });
  }

  listadoActividadesCompletas(): void{
    this.actividadService.listadoActividadesCompletas().subscribe(
      data => {
        this.actividades = data;
      }
    )
  }

  buscarActividad(): void{
    if (this.nombreBuscar.trim() === ''){
      this.listadoActividades();
      this.estadoBuscando = false;
      return;
    }
      
    this.actividadService.buscarActividadPorNombre(this.nombreBuscar).subscribe({
      next: (resultado) => {
        this.actividades = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreBuscar = '';
    this.listadoActividades();
  }
      
  pageActividadesEvent(): void{
    this.sharingDataService.pageActividadesEventEmitter.subscribe(pageable => {
      this.actividades = pageable.ahorros;
      this.paginator = pageable.paginator;
    })
  }

  OnSelectedActividad(actividad: Actividad): void{
    this.router.navigate(['/app/actividades/editar-actividad', actividad.idActividad])
  }
    
    
  eliminarActividad(idActividad: number): void {
    
        Swal.fire({
          title: "¿Eliminar actividad?",
          text: "La actividad se marcará como inactiva.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#d33",
          cancelButtonColor: "#3085d6",
          confirmButtonText: "Sí, eliminar",
          cancelButtonText: "Cancelar"
        }).then((result) => {
    
          if (result.isConfirmed) {
    
            this.actividadService.eliminarActividad(idActividad).subscribe({
    
              next: () => {
    
                Swal.fire(
                  "Eliminado",
                  "La actividad fue eliminada correctamente",
                  "success"
                );
    
               this.listadoActividades();
    
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
