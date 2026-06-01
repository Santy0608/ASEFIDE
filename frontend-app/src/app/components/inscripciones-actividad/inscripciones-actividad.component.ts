import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { InscripcionPorActividadService } from '../../services/inscripciones-actividad.service';
import { InscripcionesActividad } from '../../domain/inscripciones-actividad';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceInscripcionActividad } from '../../sharing-data-service/sharing-data-service-inscripcion-actividad';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-inscripciones-actividad',
  imports: [CommonModule, RouterModule, PaginatorComponent],
  templateUrl: './inscripciones-actividad.component.html',
})
export class InscripcionesActividadComponent implements OnInit{

  inscripcionesActividades: InscripcionesActividad[] = [];
  errors: any;
  paginator: any = {};

  constructor(private inscripcionActividadService: InscripcionPorActividadService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceInscripcionActividad){

  }

  ngOnInit(): void {
    this.listadoInscripcionesActividadesCompletas();
  }

  listadoInscripcionesActividad(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.inscripcionActividadService.listadoInscripcionesActividadesPaginacion(page);
      })
      ).subscribe(pageable => {
        this.inscripcionesActividades = pageable.content as InscripcionesActividad[];
        this.paginator = pageable;
    });
  }

  listadoInscripcionesActividadesCompletas(): void{
    this.inscripcionActividadService.listadoInscripcionesActividadesCompletas().subscribe(
      data => {
        this.inscripcionesActividades = data;
      }
    )
  }
           
      pageInscripcioensActividadEvent(): void{
         this.sharingDataService.pageInscripcionActividadEventEmitter.subscribe(pageable => {
           this.inscripcionesActividades = pageable.inscripcionesActividades;
           this.paginator = pageable.paginator;
         })
       }
  

  OnSelectedInscripcion(inscripcionActividad: InscripcionesActividad): void{
    this.router.navigate(['/app/inscripciones-actividades/editar-inscripcion', inscripcionActividad.idInscripcion])
  }
      
      
    eliminarInscripcion(idInscripcion: number): void {
      
          Swal.fire({
            title: "¿Eliminar inscripcion?",
            text: "La inscripción se marcará como inactiva.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Sí, eliminar",
            cancelButtonText: "Cancelar"
          }).then((result) => {
      
            if (result.isConfirmed) {
      
              this.inscripcionActividadService.eliminarInscripcion(idInscripcion).subscribe({
      
                next: () => {
      
                  Swal.fire(
                    "Eliminado",
                    "La inscripción fue eliminada correctamente",
                    "success"
                  );
      
                 this.listadoInscripcionesActividad();
      
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
