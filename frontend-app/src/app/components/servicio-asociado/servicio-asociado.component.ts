import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Servicio } from '../../domain/servicio';
import { ServicioService } from '../../services/servicio.service';

@Component({
  selector: 'app-servicio-asociado',
  imports: [CommonModule, RouterModule],
  templateUrl: './servicio-asociado.component.html'
})
export class ServicioAsociadoComponent implements OnInit{

  servicios: Servicio[] = [];
  cargando: boolean = false;

  constructor(private router: Router, private servicioService: ServicioService){
    
  }

  ngOnInit(): void {
    this.listadoServicios();
  }

  verDetalle(servicio: Servicio): void{
    console.log('Ver servicio: ', servicio);
  }

  listadoServicios(): void{
    this.servicioService.listadoServiciosAsociados().subscribe(
      data => {
        this.servicios = data;
        this.cargando = false;
      }, error => {
        console.error('Error al cargar servicios', error);
      }
    );
  }


}
