import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Correo } from '../../domain/Correo';
import { CorreoService } from '../../services/correo.service';
import { SharingDataServiceCorreo } from '../../sharing-data-service/sharing-data-service-correo';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-correo-form',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './correo-form.component.html',
})
export class CorreoFormComponent implements OnInit{

  errors: any;
  correo!: Correo;

  constructor(private correoService: CorreoService,
              private sharingDataService: SharingDataServiceCorreo,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.correo = new Correo();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsCorreoFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectCorreoEventEmitter.subscribe(correo => this.correo = correo);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idCorreo') || '0');
      if (id > 0){
        this.correoService.buscarCorreoPorId(id).subscribe(correo => this.correo = correo);
      }
    })
  }

  onSubmit(correoForm: NgForm): void {

      if (correoForm.invalid) return;

      console.log("Datos enviados:", this.correo);
      this.correo.estadoId = 1;
      if (this.correo.idCorreo > 0) {

        this.correoService.editarCorreo(this.correo).subscribe({
          next: () => {
            Swal.fire({
              title: "Actualizado",
              text: "Correo actualizado correctamente",
              icon: "success"
            }).then(() => {
              this.router.navigate(['/app/correos']);
            });
          },
          error: (err) => {
            const mensaje = err.error?.mensaje || 'Error inesperado';
            Swal.fire("Error", mensaje, "error");
          }
        });

      } else {

        this.correoService.guardarCorreo(this.correo).subscribe({
          next: () => {
            Swal.fire({
              title: "Creado",
              text: "Correo creado correctamente",
              icon: "success"
            }).then(() => {
              this.router.navigate(['/app/correos']);
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
