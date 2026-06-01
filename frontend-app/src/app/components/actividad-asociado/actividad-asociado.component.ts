import { Component, OnInit } from '@angular/core';
import { Actividad } from '../../domain/Actividad';
import { Router, RouterModule } from '@angular/router';
import { ActividadService } from '../../services/actividad.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-actividad-asociado',
  imports: [CommonModule, RouterModule],
  templateUrl: './actividad-asociado.component.html',
})
export class ActividadAsociadoComponent implements OnInit{

  actividades: Actividad[] = [];
  cargando: boolean = true;

  constructor(private router: Router, private actividadService: ActividadService){

  }

  ngOnInit(): void {
    this.listadoActividades();
  }

  verDetalle(actividad: Actividad): void {
    // this.router.navigate(['/app/beneficios', beneficio.id]);
    console.log('Ver actividad:', actividad);
  }
  
  listadoActividades(): void {
    this.actividadService.listadoActividadesAsociados().subscribe(
      data => {
        this.actividades = data;
        console.log(this.actividades);
        this.cargando = false; 
      },
      error => {
        console.error('Error al cargar actividades', error);
        this.cargando = false; 
      }
    );
  }

  

}