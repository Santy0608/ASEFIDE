import { Component, OnInit } from '@angular/core';
import { Estado } from '../../domain/Estado';
import { EstadoService } from '../../services/estado.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { D } from '@angular/cdk/keycodes';

@Component({
  selector: 'app-estado',
  imports: [RouterModule, CommonModule],
  templateUrl: './estado.component.html',
  styleUrl: './estado.component.css'
})
export class EstadoComponent implements OnInit{

  estados: Estado[] = [];

  constructor(private estadoService: EstadoService, private router: Router){

  }

  ngOnInit(): void {
    this.listadoEstadosCompletos();
  }

  listadoEstados(): void{
    this.estadoService.listadoEstados().subscribe(
      data => {
        this.estados = data;
      }
    )
  }

  listadoEstadosCompletos(): void{
    this.estadoService.listadoEstadosCompletos().subscribe(
      data => {
        this.estados = data;
      }
    )
  }

  OnSelectedEstado(estado: Estado): void{
    this.router.navigate(['/app/estados/editar-estado', estado.idEstado])
  }
   
   
     eliminarEstado(idEstado: number): void {
   
       Swal.fire({
         title: "¿Eliminar estado?",
         text: "El estado se marcará como inactiva.",
         icon: "warning",
         showCancelButton: true,
         confirmButtonColor: "#d33",
         cancelButtonColor: "#3085d6",
         confirmButtonText: "Sí, eliminar",
         cancelButtonText: "Cancelar"
       }).then((result) => {
   
         if (result.isConfirmed) {
   
           this.estadoService.eliminarEstado(idEstado).subscribe({
   
             next: () => {
   
               Swal.fire(
                 "Eliminado",
                 "El estado fue eliminada correctamente",
                 "success"
               );
   
               // quitar del listado sin recargar
              this.listadoEstados();
   
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
