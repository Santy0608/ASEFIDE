import { Component, OnInit } from '@angular/core';
import { Categoria } from '../../domain/Categoria';
import { RolService } from '../../services/rol.service';
import { SharingDataServiceCategoria } from '../../sharing-data-service/sharing-data-service-categoria';
import { SharingDataServiceRol } from '../../sharing-data-service/sharing-data-service-rol';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Rol } from '../../domain/Rol';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-rol-form',
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './rol-form.component.html',
})
export class RolFormComponent implements OnInit{

  errors: any;
  rol!: Rol;

  constructor(private rolService: RolService,
              private sharingDataService: SharingDataServiceRol,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.rol = new Rol();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsRolFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectRolEventEmitter.subscribe(rol => this.rol = rol);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idRol') || '0');
      if (id > 0){
        this.rolService.buscarRolPorId(id).subscribe(rol => this.rol = rol);
      }
    })
  }

  onSubmit(rolForm: NgForm): void {


  if (rolForm.invalid) return;

  console.log("Datos enviados:", this.rol);

  if (this.rol.idRol > 0) {

    this.rolService.editarRol(this.rol).subscribe({
      next: () => {
        Swal.fire({
          title: "Actualizado",
          text: "Rol actualizado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/roles']);
        });
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  } else {

    this.rolService.guardarRol(this.rol).subscribe({
      next: () => {
        Swal.fire({
          title: "Creado",
          text: "Rol creado correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/roles']);
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
