import { Component, OnInit } from '@angular/core';
import { TipoTransaccion } from '../../domain/TipoTransaccion';
import { TipoTransaccionService } from '../../services/tipo-transaccion.service';
import { SharingDataServiceTipoAhorro } from '../../sharing-data-service/sharing-data-service-tipo-ahorro';
import { SharingDataServiceTipoTransaccion } from '../../sharing-data-service/sharing-data-service-tipo-transaccion';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tipo-transaccion-form',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './tipo-transaccion-form.component.html'
})
export class TipoTransaccionFormComponent implements OnInit{

  errors: any;
  tipoTransaccion!: TipoTransaccion;

  constructor(private tipoTransaccionService: TipoTransaccionService,
              private sharingDataService: SharingDataServiceTipoTransaccion,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.tipoTransaccion = new TipoTransaccion();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsTipoTransaccionFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectTipoTransaccionEventEmitter.subscribe(tipoTransaccion => this.tipoTransaccion = tipoTransaccion);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idTipoTransaccion') || '0');
      if (id > 0){
        this.tipoTransaccionService.buscarTipoTransaccionPorId(id).subscribe(tipoTransaccion => this.tipoTransaccion = tipoTransaccion);
      }
    })
  }

  onSubmit(tipoTransaccionForm: NgForm): void {

  if (tipoTransaccionForm.invalid) return;

  console.log("Datos enviados:", this.tipoTransaccion);

  if (this.tipoTransaccion.idTipoTransaccion > 0) {

    this.tipoTransaccionService.editarTipoTransaccion(this.tipoTransaccion).subscribe({
      next: () => {
        Swal.fire({
          title: "Actualizado",
          text: "Tipo Transaccion actualizado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/tipos-transacciones']);
        });
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  } else {

    this.tipoTransaccionService.guardarTipoTransaccion(this.tipoTransaccion).subscribe({
      next: () => {
        Swal.fire({
          title: "Creado",
          text: "Tipo Transaccion creado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/tipos-transacciones']);
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
