import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TipoAhorro } from '../../domain/TipoAhorro';
import { TipoAhorroService } from '../../services/tipo-ahorro.service';
import { SharingDataServiceTipoAhorro } from '../../sharing-data-service/sharing-data-service-tipo-ahorro';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-tipo-ahorro-form',
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './tipo-ahorro-form.component.html',
})
export class TipoAhorroFormComponent implements OnInit{

  errors: any;
  tipoAhorro!: TipoAhorro;

  constructor(private tipoAhorroService: TipoAhorroService,
              private sharingDataService: SharingDataServiceTipoAhorro,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.tipoAhorro = new TipoAhorro();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsTipoAhorroFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectTipoAhorroEventEmitter.subscribe(tipoAhorro => this.tipoAhorro = tipoAhorro);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idTipoAhorro') || '0');
      if (id > 0){
        this.tipoAhorroService.buscarTipoAhorroPorId(id).subscribe(tipoAhorro => this.tipoAhorro = tipoAhorro);
      }
    })
  }

  onSubmit(tipoAhorroForm: NgForm): void {

  if (tipoAhorroForm.invalid) return;

  console.log("Datos enviados:", this.tipoAhorro);

  if (this.tipoAhorro.idTipoAhorro > 0) {

    this.tipoAhorroService.editarTipoAhorro(this.tipoAhorro).subscribe({
      next: () => {
        Swal.fire({
          title: "Actualizado",
          text: "Tipo Ahorro actualizado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/tipos-ahorros']);
        });
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  } else {

    this.tipoAhorroService.guardarTipoAhorro(this.tipoAhorro).subscribe({
      next: () => {
        Swal.fire({
          title: "Creado",
          text: "Tipo Ahorro creado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/tipos-ahorros']);
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
