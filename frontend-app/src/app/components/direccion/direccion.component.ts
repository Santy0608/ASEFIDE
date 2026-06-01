import { Component, OnInit } from '@angular/core';
import { Direccion } from '../../domain/Direccion';
import { DireccionService } from '../../services/direccion.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceDireccion } from '../../sharing-data-service/sharing-data-service-direccion';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-direccion',
  imports: [RouterLink, RouterModule, CommonModule, PaginatorComponent, FormsModule],
  templateUrl: './direccion.component.html',
})
export class DireccionComponent implements OnInit{

  direcciones: Direccion[] = [];
  errors: any;
  paginator: any = {};
  provinciaBuscar: string = '';
  estadoBuscando: boolean = false;

  constructor(private direccionService: DireccionService, private router: Router, private route: ActivatedRoute, private SharingDataService: SharingDataServiceDireccion){
      
  }

  ngOnInit(): void {
    this.listadoDireccionesCompletas();
  }

  listadoDirecciones(): void{
      this.route.paramMap.pipe(
        switchMap(params => {
        const page = +(params.get('page') || '0');
          return this.direccionService.listadoDireccionPaginacion(page);
        })
        ).subscribe(pageable => {
          this.direcciones = pageable.content as Direccion[];
          this.paginator = pageable;
      });
    }

  listadoDireccionesCompletas(): void{
    this.direccionService.listadoDireccionesCompletas().subscribe(
      data => {
        this.direcciones = data;
      }
    )
  }
           
    pageDetallesTransaccionesEvent(): void{
      this.SharingDataService.pageDireccionEventEmitter.subscribe(pageable => {
        this.direcciones = pageable.direcciones;
        this.paginator = pageable.paginator;
      })
    }

    buscarProvincia(): void{
      if (this.provinciaBuscar.trim() === ''){
        this.listadoDirecciones();
        this.estadoBuscando = false;
        return;
      }
      
      this.direccionService.buscarDireccionPorProvincia(this.provinciaBuscar).subscribe({
        next: (resultado) => {
          this.direcciones = resultado;
          this.estadoBuscando = true;
        }, error: (err) => {
          console.log('Error', err);
        }
      })
    }

    limpiarBusqueda(): void {
      this.provinciaBuscar = '';
      this.listadoDirecciones();
    }

    OnSelectedDireccion(direccion: Direccion): void{
      this.router.navigate(['/app/direcciones/editar-direccion', direccion.idDireccion])
    }
  
  
    eliminarDireccion(idDireccion: number): void {
  
      Swal.fire({
        title: "¿Eliminar dirección?",
        text: "La dirección se marcará como inactiva.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
  
        if (result.isConfirmed) {
  
          this.direccionService.eliminarDireccion(idDireccion).subscribe({
  
            next: () => {
  
              Swal.fire(
                "Eliminado",
                "La dirección fue eliminada correctamente",
                "success"
              );
  
              // quitar del listado sin recargar
             this.listadoDirecciones();
  
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
