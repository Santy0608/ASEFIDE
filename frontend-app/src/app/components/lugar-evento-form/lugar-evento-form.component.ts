import { Component, OnInit } from '@angular/core';
import { LugarEvento } from '../../domain/LugarEvento';
import { LugarEventoService } from '../../services/lugar-evento.service';
import { SharingDataServiceLugarEvento } from '../../sharing-data-service/sharing-data-service-lugar-evento';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lugar-evento-form',
  imports: [RouterModule, CommonModule, RouterLink, FormsModule],
  templateUrl: './lugar-evento-form.component.html',
  styleUrl: './lugar-evento-form.component.css'
})
export class LugarEventoFormComponent implements OnInit{

  errors: any;
  lugarEvento!: LugarEvento;

  constructor(private lugarEventoService: LugarEventoService,
              private sharingDataService: SharingDataServiceLugarEvento,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.lugarEvento = new LugarEvento();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsLugarEventoFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectLugarEventoEventEmitter.subscribe(lugarEvento => this.lugarEvento = lugarEvento);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idLugarEvento') || '0');
      if (id > 0){
        this.lugarEventoService.buscarLugarEventoPorId(id).subscribe(lugarEvento => this.lugarEvento = lugarEvento);
      }
    })
  }

  onSubmit(lugarEventoForm: NgForm): void {

  if (lugarEventoForm.invalid) return;

  console.log("Datos enviados:", this.lugarEvento);

  if (this.lugarEvento.idLugarEvento > 0) {

    this.lugarEventoService.editarLugarEvento(this.lugarEvento).subscribe({
      next: () => {
        Swal.fire({
          title: "Actualizado",
          text: "Lugar Evento actualizado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/lugares-eventos']);
        });
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  } else {

    this.lugarEventoService.guardarLugarEvento(this.lugarEvento).subscribe({
      next: () => {
        Swal.fire({
          title: "Creado",
          text: "Lugar Evento creado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/lugares-eventos']);
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
