import { Component, OnInit } from '@angular/core';
import { TipoTransaccion } from '../../domain/TipoTransaccion';
import { TipoTransaccionService } from '../../services/tipo-transaccion.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceTipoTransaccion } from '../../sharing-data-service/sharing-data-service-tipo-transaccion';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tipo-transaccion',
  imports: [CommonModule, RouterModule, PaginatorComponent, FormsModule],
  templateUrl: './tipo-transaccion.component.html',
})
export class TipoTransaccionComponent implements OnInit{

  tiposTransacciones: TipoTransaccion[] = [];
  errors: any;
  paginator: any = {};
  nombreTipoTransaccionBuscar: string = '';
  estadoBuscando: boolean = false;

  constructor(private tipoTransaccionService: TipoTransaccionService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceTipoTransaccion)
  {

  }

  ngOnInit(): void {
    this.listadoTiposTransaccionesCompletas();
  }

   listadoTiposTransacciones(): void{
      this.route.paramMap.pipe(
        switchMap(params => {
        const page = +(params.get('page') || '0');
          return this.tipoTransaccionService.listadoTipoTransaccionPaginacion(page);
        })
        ).subscribe(pageable => {
          this.tiposTransacciones = pageable.content as TipoTransaccion[];
          this.paginator = pageable;
      });
    }

    listadoTiposTransaccionesCompletas(): void{
      this.tipoTransaccionService.listadoTiposTransaccionesCompletas().subscribe(
        data => {
          this.tiposTransacciones = data;
        }
      )
    }
            
    pageTipoTransaccionesEvent(): void{
      this.sharingDataService.pageTipoTransaccionEventEmitter.subscribe(pageable => {
        this.tiposTransacciones = pageable.tiposTransacciones;
        this.paginator = pageable.paginator;
      })
    }

   OnSelectedTipoTransaccion(tipoTransaccion: TipoTransaccion): void{
      this.router.navigate(['/app/tipos-transacciones/editar-tipo-transaccion', tipoTransaccion.idTipoTransaccion])
    }
    
  buscarTipoTransaccion(): void{
    if (this.nombreTipoTransaccionBuscar.trim() === ''){
      this.listadoTiposTransacciones();
      this.estadoBuscando = false;
      return;
    }
        
    this.tipoTransaccionService.buscarTipoTransaccionPorNombre(this.nombreTipoTransaccionBuscar).subscribe({
      next: (resultado) => {
        this.tiposTransacciones = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreTipoTransaccionBuscar = '';
    this.listadoTiposTransacciones();
  }
    
    eliminarTipoTransaccion(idTipoTransaccion: number): void {
    
        Swal.fire({
          title: "¿Eliminar tipo transacción?",
          text: "El tipo de transacción se marcará como inactivo.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#d33",
          cancelButtonColor: "#3085d6",
          confirmButtonText: "Sí, eliminar",
          cancelButtonText: "Cancelar"
        }).then((result) => {
    
          if (result.isConfirmed) {
    
            this.tipoTransaccionService.eliminarTipoTransaccion(idTipoTransaccion).subscribe({
    
              next: () => {
    
                Swal.fire(
                  "Eliminado",
                  "El tipo de transacción fue eliminada correctamente",
                  "success"
                );
    
                // quitar del listado sin recargar
               this.listadoTiposTransacciones();
    
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
