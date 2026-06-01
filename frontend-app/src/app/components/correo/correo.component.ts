import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CorreoService } from '../../services/correo.service';
import { Correo } from '../../domain/Correo';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceCorreo } from '../../sharing-data-service/sharing-data-service-correo';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-correo',
  imports: [CommonModule, RouterModule, PaginatorComponent, FormsModule],
  templateUrl: './correo.component.html',
})
export class CorreoComponent implements OnInit{

  correos: Correo[] = [];
  correoElectronicoBuscar: string = '';
  paginator: any = {};
  estadoBuscando: boolean = false;


  constructor(private correoService: CorreoService,
    private router: Router,
    private route: ActivatedRoute,
    private sharingDataService: SharingDataServiceCorreo
  ){

  }


  ngOnInit(): void {
    this.listadoCorreosCompletos();
  }

  listadoCorreos(): void{
     this.route.paramMap.pipe(
       switchMap(params => {
       const page = +(params.get('page') || '0');
         return this.correoService.listadoCorreosPaginacion(page);
       })
       ).subscribe(pageable => {
         this.correos = pageable.content as Correo[];
         this.paginator = pageable;
       });
   }

   listadoCorreosCompletos(): void{
      this.correoService.listadoCorreosCompletos().subscribe(
        data => {
          this.correos = data;
        }
      )
   }

   buscarCorreoElectronico(): void{
    if (this.correoElectronicoBuscar.trim() === ''){
      this.listadoCorreos();
      this.estadoBuscando = false;
      return;
    }
    
    this.correoService.buscarCorreo(this.correoElectronicoBuscar).subscribe({
      next: (resultado) => {
        this.correos = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.correoElectronicoBuscar = '';
    this.listadoCorreos();
  }
       
   pageTelefonosEvent(): void{
     this.sharingDataService.pageCorreosEventEmitter.subscribe(pageable => {
       this.correos = pageable.ahorros;
       this.paginator = pageable.paginator;
     })
   }

  OnSelectedCorreo(correo: Correo): void{
    this.router.navigate(['/app/correos/editar-correo', correo.idCorreo])
  }
  
  
  eliminarCorreo(idCorreo: number): void {
      if (!idCorreo) {
        console.warn('No se puede eliminar un correo sin ID válido');
        return;
      }
      
      Swal.fire({
        title: "¿Eliminar correo?",
        text: "El correo se marcará como inactivo.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
  
        if (result.isConfirmed) {
  
          this.correoService.eliminarCorreo(idCorreo).subscribe({
  
            next: () => {
  
              Swal.fire(
                "Eliminado",
                "El correo fue eliminado correctamente",
                "success"
              );
  
              // quitar del listado sin recargar
             this.listadoCorreos();
  
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
