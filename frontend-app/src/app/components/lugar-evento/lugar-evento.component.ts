import { Component, OnInit } from '@angular/core';
import { LugarEvento } from '../../domain/LugarEvento';
import { LugarEventoService } from '../../services/lugar-evento.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceLugarEvento } from '../../sharing-data-service/sharing-data-service-lugar-evento';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-lugar-evento',
  imports: [RouterModule, CommonModule, RouterLink, PaginatorComponent, FormsModule],
  templateUrl: './lugar-evento.component.html',
})
export class LugarEventoComponent implements OnInit{

  lugaresEventos: LugarEvento[] = [];
  errors: any;
  paginator: any = {};
  nombreLugarEventoBuscar: string = '';
  estadoBuscando: boolean = false;


  constructor(private lugarEventoService: LugarEventoService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceLugarEvento){
    
  }

  ngOnInit(): void {
    this.listadoLugaresEventosCompletos();
  }

  listadoLugaresEventos(): void{
      this.route.paramMap.pipe(
        switchMap(params => {
        const page = +(params.get('page') || '0');
          return this.lugarEventoService.listadoLugaresEventosPaginacion(page);
        })
        ).subscribe(pageable => {
          this.lugaresEventos = pageable.content as LugarEvento[];
          this.paginator = pageable;
      });
  }

  listadoLugaresEventosCompletos(): void{
    this.lugarEventoService.listadoLugaresEventosCompletos().subscribe(
      data => {
        this.lugaresEventos = data;
      }
    )
  }
             
  pageLugaresEventosEvent(): void{
    this.sharingDataService.pageLugarEventoEventEmitter.subscribe(pageable => {
      this.lugaresEventos = pageable.lugaresEventos;
      this.paginator = pageable.paginator;
    })
  }
    

  OnSelectedLugarEvento(lugarEvento: LugarEvento): void{
    this.router.navigate(['/app/lugares-eventos/actualizar-lugar-evento', lugarEvento.idLugarEvento])
  }
  
  buscarLugarEvento(): void{
    if (this.nombreLugarEventoBuscar.trim() === ''){
      this.listadoLugaresEventos();
      this.estadoBuscando = false;
      return;
    }
      
    this.lugarEventoService.buscarLugarEventoPorNombre(this.nombreLugarEventoBuscar).subscribe({
      next: (resultado) => {
        this.lugaresEventos = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreLugarEventoBuscar = '';
    this.listadoLugaresEventos();
  }

  eliminarLugarEvento(idLugarEvento: number): void {
  
      Swal.fire({
        title: "¿Eliminar lugar vento?",
        text: "El lugar evento se marcará como inactiva.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
  
        if (result.isConfirmed) {
  
          this.lugarEventoService.eliminarLugarEvento(idLugarEvento).subscribe({
  
            next: () => {
  
              Swal.fire(
                "Eliminado",
                "El lugar evento fue eliminado correctamente",
                "success"
              );
  
              // quitar del listado sin recargar
             this.listadoLugaresEventos();
  
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
