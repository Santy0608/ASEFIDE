import { Component, OnInit } from '@angular/core';
import { PuestoEmpresa } from '../../domain/PuestoEmpresa';
import { PuestoEmpresaService } from '../../services/puesto-empresa.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServicePuestoEmpresa } from '../../sharing-data-service/sharing-data-service-puesto-empresa';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-puesto-empresa',
  imports: [CommonModule, RouterModule, PaginatorComponent, FormsModule],
  templateUrl: './puesto-empresa.component.html',
})
export class PuestoEmpresaComponent implements OnInit{

  puestosEmpresas: PuestoEmpresa[] = [];
  errors: any;
  paginator: any = {};
  puestoEmpresaBuscar: string = '';
  estadoBuscando: boolean = false;

  constructor(private puestoEmpresaService: PuestoEmpresaService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServicePuestoEmpresa){

  }
  
  ngOnInit(): void {
    this.listadoPuestosEmpresasCompletos();
  }

  listadoPuestoEmpresas(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
      const page = +(params.get('page') || '0');
        return this.puestoEmpresaService.listadoPuestoEmpresaPaginacion(page);
      })
      ).subscribe(pageable => {
        this.puestosEmpresas = pageable.content as PuestoEmpresa[];
        this.paginator = pageable;
      });
  }

  listadoPuestosEmpresasCompletos(): void{
    this.puestoEmpresaService.listadoPuestosEmpresasCompletos().subscribe(
      data => {
        this.puestosEmpresas = data;
      }
    )
  }
                       
      pagePuestoEmpresasEvent(): void{
        this.sharingDataService.pagePuestoEmpresaEventEmitter.subscribe(pageable => {
          this.puestosEmpresas = pageable.puestosEmpresas;
          this.paginator = pageable.paginator;
        })
      }

  buscarPuestoEmrpesa(): void{
    if (this.puestoEmpresaBuscar){
      this.listadoPuestoEmpresas();
      this.estadoBuscando = false;
      return;
    }

    this.puestoEmpresaService.buscarPuestoEmpresa(this.puestoEmpresaBuscar).subscribe({
      next: (resultado) => {
        this.puestosEmpresas = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  
  limpiarBusqueda(): void {
    this.puestoEmpresaBuscar = '';
    this.listadoPuestoEmpresas();
  }
  

  OnSelectedPuestoEmpresa(puestoEmpresa: PuestoEmpresa): void{
    this.router.navigate(['/app/puestos-empresas/editar-puesto-empresa', puestoEmpresa.idPuestoEmpresa])
  }
  
  
    eliminarPuestoEmpresa(idPuestoEmpresa: number): void {
  
      Swal.fire({
        title: "¿Eliminar puesto empresa?",
        text: "El puesto empresa se marcará como inactivo.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
  
        if (result.isConfirmed) {
  
          this.puestoEmpresaService.eliminarPuestoEmpresa(idPuestoEmpresa).subscribe({
  
            next: () => {
  
              Swal.fire(
                "Eliminado",
                "El puesto empresa fue eliminado correctamente",
                "success"
              );
  
              // quitar del listado sin recargar
             this.listadoPuestoEmpresas();
  
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
