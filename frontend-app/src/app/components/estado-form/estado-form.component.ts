import { Component, OnInit } from '@angular/core';
import { Estado } from '../../domain/Estado';
import { EstadoService } from '../../services/estado.service';
import { SharingDataServiceEstado } from '../../sharing-data-service/sharing-data-service-estado';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-estado-form',
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './estado-form.component.html',
})
export class EstadoFormComponent implements OnInit{

  errors: any;
  estado!: Estado;

  constructor(private estadoService: EstadoService,
              private sharingDataService: SharingDataServiceEstado,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.estado = new Estado();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsEstadoFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectEstadoEventEmitter.subscribe(estado => this.estado = estado);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idEstado') || '0');
      if (id > 0){
        this.estadoService.buscarEstadoPorId(id).subscribe(estado => this.estado = estado);
      }
    })
  }

  onSubmit(estadoForm: NgForm): void {

    if (estadoForm.invalid) return;

    console.log("Datos enviados:", this.estado);

    if (this.estado.idEstado > 0) {

      this.estadoService.editarEstado(this.estado).subscribe({
        next: () => {
          Swal.fire({
            title: "Actualizado",
            text: "Estado actualizado correctamente",
            icon: "success"
          }).then(() => {
            this.router.navigate(['/app/estados']);
          });
        },
        error: (err) => {
          const mensaje = err.error?.mensaje || 'Error inesperado';
          Swal.fire("Error", mensaje, "error");
        }
      });

    } else {

      this.estadoService.guardarEstado(this.estado).subscribe({
        next: () => {
          Swal.fire({
            title: "Creado",
            text: "Estado creado correctamente",
            icon: "success"
          }).then(() => {
            this.router.navigate(['/app/estados']);
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
