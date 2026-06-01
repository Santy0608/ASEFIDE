import { Component, OnInit } from '@angular/core';
import { Beneficio } from '../../domain/Beneficio';
import { BeneficioService } from '../../services/beneficio.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { SharingDataServiceBeneficio } from '../../sharing-data-service/sharing-data-service-beneficio';
import { PaginatorComponent } from '../paginator/paginator.component';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-beneficio',
  imports: [CommonModule, RouterModule, RouterLink, PaginatorComponent, FormsModule],
  templateUrl: './beneficio.component.html'
})
export class BeneficioComponent implements OnInit{

  errors: any;
  beneficios: Beneficio[] = [];
  paginator: any = {};
  nombreBeneficioBuscar: string = '';
  estadoBuscando: boolean = false;

  constructor(private beneficioService: BeneficioService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceBeneficio){
        
  }

  ngOnInit(): void {
    this.listadoBeneficiosCompletos();
  }

  listadoBeneficios(): void{
      this.route.paramMap.pipe(
        switchMap(params => {
          const page = +(params.get('page') || '0');
          return this.beneficioService.listadoBeneficiosPaginacion(page);
        })
        ).subscribe(pageable => {
          this.beneficios = pageable.content as Beneficio[];
          this.paginator = pageable;
        });
  }

  listadoBeneficiosCompletos(): void{
    this.beneficioService.listadoBeneficiosCompletos().subscribe(
      data => {
        this.beneficios = data;
      }
    )
  }
        
    pageBeneficiosEvent(): void{
      this.sharingDataService.pageBeneficiosEventEmitter.subscribe(pageable => {
        this.beneficios = pageable.ahorros;
        this.paginator = pageable.paginator;
      })
    }

  buscarBeneficio(): void{
    if (this.nombreBeneficioBuscar.trim() === ''){
      this.listadoBeneficios();
      this.estadoBuscando = false;
      return;
    }
      
    this.beneficioService.buscarBeneficioPorNombre(this.nombreBeneficioBuscar).subscribe({
      next: (resultado) => {
        this.beneficios = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreBeneficioBuscar = '';
    this.listadoBeneficios();
  }
      

  OnSelectedBeneficio(beneficio: Beneficio): void{
    this.router.navigate(['/app/beneficios/editar-beneficio', beneficio.idBeneficio])
  }
      
      
  eliminarBeneficio(idBeneficio: number): void {
      
          Swal.fire({
            title: "¿Eliminar beneficio?",
            text: "El beneficio se marcará como inactivo.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Sí, eliminar",
            cancelButtonText: "Cancelar"
          }).then((result) => {
      
            if (result.isConfirmed) {
      
              this.beneficioService.eliminarBeneficio(idBeneficio).subscribe({
      
                next: () => {
      
                  Swal.fire(
                    "Eliminado",
                    "El beneficio fue eliminado correctamente",
                    "success"
                  );
      
                 this.listadoBeneficios();
      
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
