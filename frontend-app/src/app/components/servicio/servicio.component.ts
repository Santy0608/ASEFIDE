import { Component, OnInit } from '@angular/core';
import { ServicioService } from '../../services/servicio.service';
import { Servicio } from '../../domain/servicio';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { switchMap } from 'rxjs';
import { SharingDataServiceServicio } from '../../sharing-data-service/sharing-data-service-servicio';
import { PaginatorComponent } from '../paginator/paginator.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-servicio',
  imports: [RouterModule, CommonModule, RouterLink, PaginatorComponent, FormsModule],
  templateUrl: './servicio.component.html',
})
export class ServicioComponent implements OnInit{

  errors: any;
  servicios: Servicio[] = [];
  paginator: any = {};
  nombreServicioBuscar: string = '';
  estadoBuscando: boolean = false;

  constructor(private servicioServie: ServicioService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceServicio){

  } 

  ngOnInit(): void {
    this.listadoServiciosCompletos();
  }

  listadoServicios(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.servicioServie.listadoServicioPaginacion(page);
      })
      ).subscribe(pageable => {
        this.servicios = pageable.content as Servicio[];
        this.paginator = pageable;
      });
  }

  listadoServiciosCompletos(): void{
    this.servicioServie.listadoServiciosCompletos().subscribe(
      data => {
        this.servicios = data;
      }
    )
  }
        
    pageServiciosEvent(): void{
      this.sharingDataService.pageServiciosEventEmitter.subscribe(pageable => {
        this.servicios = pageable.servicios;
        this.paginator = pageable.paginator;
      })
    }

  OnSelectedServicio(servicio: Servicio): void{
    this.router.navigate(['/app/servicios/editar-servicio', servicio.idServicio])
  }

   buscarServicio(): void{
    if (this.nombreServicioBuscar.trim() === ''){
      this.listadoServicios();
      this.estadoBuscando = false;
      return;
    }
      
    this.servicioServie.buscarServicioPorNombre(this.nombreServicioBuscar).subscribe({
      next: (resultado) => {
        this.servicios = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreServicioBuscar = '';
    this.listadoServicios();
  }
      
      
      
  eliminarServicio(idServicio: number): void {
      
          Swal.fire({
            title: "¿Eliminar servicio?",
            text: "El servicio se marcará como inactiva.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Sí, eliminar",
            cancelButtonText: "Cancelar"
          }).then((result) => {
      
            if (result.isConfirmed) {
      
              this.servicioServie.eliminarServicio(idServicio).subscribe({
      
                next: () => {
      
                  Swal.fire(
                    "Eliminado",
                    "El servicio fue eliminado correctamente",
                    "success"
                  );
      
                 this.listadoServicios();
      
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
