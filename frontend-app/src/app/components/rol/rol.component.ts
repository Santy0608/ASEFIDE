import { Component, OnInit } from '@angular/core';
import { RolService } from '../../services/rol.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Rol } from '../../domain/Rol';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-rol',
  imports: [RouterModule, CommonModule],
  templateUrl: './rol.component.html',
})
export class RolComponent implements OnInit{

  roles: Rol[] = [];
  errors: any;

  constructor(private rolService: RolService, private router: Router){
    
  }

  ngOnInit(): void {
    this.listadoRolesCompletos();
  }

  listadoRoles(): void{
    this.rolService.listadoRoles().subscribe(
      data => {
        this.roles = data;
      }
    )
  }

  listadoRolesCompletos(): void{
    this.rolService.listadoRolesCompletos().subscribe(
      data => {
        this.roles = data;
      }
    )
  }

   OnSelectedRol(rol: Rol): void{
     this.router.navigate(['/app/roles/editar-rol', rol.idRol])
   }
  
  
    eliminarRol(idRol: number): void {
  
      Swal.fire({
        title: "¿Eliminar rol?",
        text: "El rol se marcará como inactivo.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
  
        if (result.isConfirmed) {
  
          this.rolService.eliminarRol(idRol).subscribe({
  
            next: () => {
  
              Swal.fire(
                "Eliminado",
                "El rol fue eliminado correctamente",
                "success"
              );
  
              // quitar del listado sin recargar
             this.listadoRoles();
  
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
