import { Component, OnInit } from '@angular/core';
import { TipoAhorroService } from '../../services/tipo-ahorro.service';
import { TipoAhorro } from '../../domain/TipoAhorro';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { SharingDataServiceTipoAhorro } from '../../sharing-data-service/sharing-data-service-tipo-ahorro';
import { switchMap } from 'rxjs';
import { PaginatorComponent } from '../paginator/paginator.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tipo-ahorro',
  imports: [RouterLink, CommonModule, RouterModule, PaginatorComponent, FormsModule],
  templateUrl: './tipo-ahorro.component.html',
})
export class TipoAhorroComponent implements OnInit{

  tiposAhorros: TipoAhorro[] = [];
  errors: any;
  paginator: any = {};
  nombreTipoAhorroBuscar: string  = '';
  estadoBuscando: boolean = false;

  constructor(private tipoAhorroService: TipoAhorroService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceTipoAhorro){

  }

  ngOnInit(): void {
    this.listadoTiposAhorrosCompletos();
  }

  listadoTiposAhorros(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.tipoAhorroService.listadoTipoAhorroPaginacion(page);
      })
      ).subscribe(pageable => {
        this.tiposAhorros = pageable.content as TipoAhorro[];
        this.paginator = pageable;
      });
  }

  listadoTiposAhorrosCompletos(): void{
    this.tipoAhorroService.listadoTiposAhorrosCompletos().subscribe(
      data => {
        this.tiposAhorros = data;
      }
    )
  }
        
    pageTipoAhorrosEvent(): void{
      this.sharingDataService.pageTiposAhorroEventEmitter.subscribe(pageable => {
        this.tiposAhorros = pageable.tiposAhorros;
        this.paginator = pageable.paginator;
      })
    }

  buscarTipoAhorro(): void{
    if (this.nombreTipoAhorroBuscar.trim() === ''){
      this.listadoTiposAhorros();
      this.estadoBuscando = false;
      return;
    }
      
    this.tipoAhorroService.buscarTipoAhorroPorNombre(this.nombreTipoAhorroBuscar).subscribe({
      next: (resultado) => {
        this.tiposAhorros = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreTipoAhorroBuscar = '';
    this.listadoTiposAhorros();
  }

  
  OnSelectedTipoAhorro(tipoAhorro: TipoAhorro): void{
    this.router.navigate(['/app/tipos-ahorros/editar-tipo-ahorro', tipoAhorro.idTipoAhorro])
  }
  
  
  eliminarTipoAhorro(idTipoAhorro: number): void {
  
      Swal.fire({
        title: "¿Eliminar tipo ahorro?",
        text: "El tipo de ahorro se marcará como inactivo.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
  
        if (result.isConfirmed) {
  
          this.tipoAhorroService.eliminarTipoAhorro(idTipoAhorro).subscribe({
  
            next: () => {
  
              Swal.fire(
                "Eliminado",
                "El tipo de ahorro fue eliminada correctamente",
                "success"
              );
  
              // quitar del listado sin recargar
             this.listadoTiposAhorros();
  
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
