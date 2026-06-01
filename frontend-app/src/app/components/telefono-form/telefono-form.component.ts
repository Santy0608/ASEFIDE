import { Component, NgModule, OnInit } from '@angular/core';
import { Telefono } from '../../domain/Telefono';
import { TelefonoService } from '../../services/telefono.service';
import { SharingDataServiceTelefono } from '../../sharing-data-service/sharing-data-service-telefono';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-telefono-form',
  imports: [CommonModule, RouterModule, RouterLink, FormsModule],
  templateUrl: './telefono-form.component.html',
})
export class TelefonoFormComponent implements OnInit{

  errors: any;
  telefono!: Telefono;

  constructor(private telefonoService: TelefonoService,
              private sharingDataService: SharingDataServiceTelefono,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.telefono = new Telefono();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsTelefonosFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectTelefonoEventEmitter.subscribe(telefono => this.telefono = telefono);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idTelefono') || '0');
      if (id > 0){
        this.telefonoService.buscarTelefonoPorId(id).subscribe(telefono => this.telefono = telefono);
      }
    })
  }

  onSubmit(telefonoForm: NgForm): void {

      if (telefonoForm.invalid) return;

      console.log("Datos enviados:", this.telefono);
      this.telefono.estadoId = 1;
      if (this.telefono.idTelefono > 0) {

        this.telefonoService.editarTelefono(this.telefono).subscribe({
          next: () => {
            Swal.fire({
              title: "Actualizado",
              text: "Telefono actualizado correctamente",
              icon: "success"
            }).then(() => {
              this.router.navigate(['/app/telefonos']);
            });
          },
          error: (err) => {
            const mensaje = err.error?.mensaje || 'Error inesperado';
            Swal.fire("Error", mensaje, "error");
          }
        });

      } else {

        const nuevoTelefono = {
          numeroTelefono: this.telefono.numeroTelefono,
          estadoId: this.telefono.estadoId
        };

        this.telefonoService.guardarTelefono(this.telefono).subscribe({
          next: () => {
            Swal.fire({
              title: "Creado",
              text: "Telefono creado correctamente",
              icon: "success"
            }).then(() => {
              this.router.navigate(['/app/telefonos']);
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
