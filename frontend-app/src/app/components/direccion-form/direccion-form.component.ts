import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm, NgModel } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { Direccion } from '../../domain/Direccion';
import { DireccionService } from '../../services/direccion.service';
import { SharingDataServiceDireccion } from '../../sharing-data-service/sharing-data-service-direccion';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-direccion-form',
  imports: [CommonModule, RouterModule, FormsModule, RouterLink],
  templateUrl: './direccion-form.component.html'
})
export class DireccionFormComponent implements OnInit{

  errors: any;
  direccion!: Direccion;

  constructor(private direccionService: DireccionService,
              private sharingDataService: SharingDataServiceDireccion,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.direccion = new Direccion();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsDireccionFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectDireccionEventEmitter.subscribe(direccion => this.direccion = direccion);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idDireccion') || '0');
      if (id > 0){
        this.direccionService.buscarDireccionPorId(id).subscribe(direccion => this.direccion = direccion);
      }
    })
  }

  onSubmit(direccionForm: NgForm): void {

  if (direccionForm.invalid) return;

  console.log("Datos enviados:", this.direccion);

  if (this.direccion.idDireccion > 0) {

    this.direccionService.editarDireccion(this.direccion).subscribe({
      next: () => {
        Swal.fire({
          title: "Actualizado",
          text: "Direccion actualizada correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/direcciones']);
        });
      },
      error: (err: any) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  } else {

    this.direccionService.guardarDireccion(this.direccion).subscribe({
      next: () => {
        Swal.fire({
          title: "Creado",
          text: "Direccion creada correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/direcciones']);
        });
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  }

}

}
