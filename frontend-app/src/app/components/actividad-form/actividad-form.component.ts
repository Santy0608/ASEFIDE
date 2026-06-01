import { Component, OnInit } from '@angular/core';
import { Actividad } from '../../domain/Actividad';
import { LugarEvento } from '../../domain/LugarEvento';
import { Usuario } from '../../domain/Usuario';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServiceActividad } from '../../sharing-data-service/sharing-data-service-actividad';
import { LugarEventoService } from '../../services/lugar-evento.service';
import { ActividadService } from '../../services/actividad.service';
import { FormsModule, NgForm } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-actividad-form',
  imports: [FormsModule, NgSelectModule, CommonModule, RouterModule],
  templateUrl: './actividad-form.component.html',
})
export class ActividadFormComponent implements OnInit{

  errors: any;
  actividad!: Actividad;
  lugaresEventos: LugarEvento[] = [];
  usuarios: Usuario[] = [];


  constructor(private usuarioService: UsuarioService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServiceActividad,
              private lugarEventoService: LugarEventoService,
              private actividadService: ActividadService
  ){
    this.actividad = new Actividad();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsActividadFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectActividadEventEmitter.subscribe(actividad => this.actividad = actividad);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idActividad') || '0');
      if (id > 0){
        this.actividadService.buscarActividadPorId(id).subscribe(actividad => this.actividad = actividad);
      }
    })

    this.cargarUsuarios();
    this.cargarLugaresEventos();
  }

  cargarUsuarios(): void{
    this.usuarioService.listadoUsuarios().subscribe(data => {
      this.usuarios = data.map(u => ({
        ...u, 
        nombreCompleto: `${u.nombre} ${u.apellidoPaterno}` 
      }));
    });   
  }

  
  onSubmit(actividadForm: NgForm): void {
  if (actividadForm.invalid) return;

  // Aplanamos el objeto para el backend
  const actividadToSend = {
    idActividad: this.actividad.idActividad,
    nombre: this.actividad.nombre,
    descripcion: this.actividad.descripcion,
    fechaEvento: this.actividad.fechaEvento,
    cupoTotal: this.actividad.cupoTotal,
  
    // Mapeo exacto al DTO de Java
    estadoId: Number(this.actividad.estadoId),
    usuarioId: Number(this.actividad.usuarioId),
    lugarEventoId: Number(this.actividad.lugarEventoId)
  };
  console.log(JSON.stringify(actividadToSend, null, 2)); 
  const request$ = this.actividad.idActividad > 0 
    ? this.actividadService.editarActividad(actividadToSend) 
    : this.actividadService.guardarActividad(actividadToSend);

  request$.subscribe({
    next: () => {
      Swal.fire("Éxito", "Operación realizada correctamente", "success");
      this.router.navigate(['/app/cuentas-ahorros']);
    },
    error: (err) => {
      const mensaje = err.error?.mensaje || 'Error inesperado';
      Swal.fire("Error", mensaje, "error");
      this.sharingDataService.errorsActividadFormEventEmitter.emit(err);
    }
  });
}


  

  cargarLugaresEventos(): void{
    this.lugarEventoService.listadoLugaresEventos().subscribe(
      data => {
        this.lugaresEventos = data;
        console.log("Datos recibidos de lugares eventos:", data)
      }
    )
  }


}
