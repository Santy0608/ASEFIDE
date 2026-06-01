import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Telefono } from '../../domain/Telefono';
import { TelefonoService } from '../../services/telefono.service';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { switchMap } from 'rxjs';
import { SharingDataServiceTelefono } from '../../sharing-data-service/sharing-data-service-telefono';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-telefono',
  imports: [CommonModule, RouterModule, PaginatorComponent, FormsModule],
  templateUrl: './telefono.component.html',
})
export class TelefonoComponent implements OnInit{

  telefonos: Telefono[] = [];

  paginator: any = {};
  numeroTelefonoBuscar: string = '';
  estadoBuscando: boolean = false;


  constructor(private telefonoService: TelefonoService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceTelefono){
    
  }

  ngOnInit(): void {
    this.listadoTelefonosCompletos();
  }

  listadoTelefonos(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.telefonoService.listadoTelefonosPaginacion(page);
      })
      ).subscribe(pageable => {
        this.telefonos = pageable.content as Telefono[];
        this.paginator = pageable;
      });
  }

  listadoTelefonosCompletos(): void{
    this.telefonoService.listadoTelefonosCompletos().subscribe(
      data => {
        this.telefonos = data;
      }
    )
  }

  buscarNumeroTelefono(): void{
    if (this.numeroTelefonoBuscar.trim() === ''){
      this.listadoTelefonos();
      this.estadoBuscando = false;
      return;
    }
    
    this.telefonoService.buscarPorNumeroTelefono(this.numeroTelefonoBuscar).subscribe({
      next: (resultado) => {
        this.telefonos = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.numeroTelefonoBuscar = '';
    this.listadoTelefonos();
  }
      
  pageTelefonosEvent(): void{
    this.sharingDataService.pageTelefonosEventEmitter.subscribe(pageable => {
      this.telefonos = pageable.ahorros;
      this.paginator = pageable.paginator;
    })
  }

  OnSelectedTelefono(telefono: Telefono): void{
    this.router.navigate(['/app/telefonos/editar-telefono', telefono.idTelefono])
  }
    
    
  eliminarTelefono(idTelefono: number): void {
    if (!idTelefono) {
        console.warn('No se puede eliminar un telefono sin ID válido');
        return;
    }
        
    Swal.fire({
        title: "¿Eliminar telefono?",
        text: "El telefono se marcará como inactivo.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
    }).then((result) => {
    
          if (result.isConfirmed) {
    
            this.telefonoService.eliminarTelefono(idTelefono).subscribe({
    
              next: () => {
    
                Swal.fire(
                  "Eliminado",
                  "El telefono fue eliminado correctamente",
                  "success"
                );
    
                // quitar del listado sin recargar
               this.listadoTelefonos();
    
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
