import { Component, OnInit } from '@angular/core';
import { InscripcionesActividad } from '../../domain/inscripciones-actividad';
import { Actividad } from '../../domain/Actividad';
import { Usuario } from '../../domain/Usuario';
import { InscripcionPorActividadService } from '../../services/inscripciones-actividad.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServiceInscripcionActividad } from '../../sharing-data-service/sharing-data-service-inscripcion-actividad';
import { UsuarioService } from '../../services/usuario.service';
import { ActividadService } from '../../services/actividad.service';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inscripciones-actividad-form',
  imports: [NgSelectModule, FormsModule, RouterModule, CommonModule],
  templateUrl: './inscripciones-actividad-form.component.html',
})
export class InscripcionesActividadFormComponent implements OnInit{


  errors: any;
  inscripcionActividad!: InscripcionesActividad;
  actividades: Actividad[] = [];
  usuarios: Usuario[] = [];


  constructor(private inscripcionActividadService: InscripcionPorActividadService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServiceInscripcionActividad,
              private usuarioService: UsuarioService,
              private actividadService: ActividadService
  ){
    this.inscripcionActividad = new InscripcionesActividad();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsInscripcionActividadFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectInscripcionActividadEventEmitter.subscribe(inscripcionActividad => this.inscripcionActividad = inscripcionActividad);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idInscripcion') || '0');
      if (id > 0){
        this.inscripcionActividadService.buscarInscripcionPorActividadPorId(id).subscribe(inscripcionActividad => this.inscripcionActividad = inscripcionActividad);
      }
    })

    this.cargarUsuarios();
    this.cargarActividades();
  }

  cargarUsuarios(): void{
    this.usuarioService.listarUsuariosCompletos().subscribe(data => {
      this.usuarios = data.map(u => ({
        ...u, 
        nombreCompleto: `${u.nombre} ${u.apellidoPaterno}` 
      }));
    });   
  }

  
  onSubmit(inscripcionForm: NgForm): void {
  if (inscripcionForm.invalid) return;

  // Aplanamos el objeto para el backend
  const inscripcionActividadToSend = {
    idInscripcion: this.inscripcionActividad.idInscripcion,
    fechaInscripcion: this.inscripcionActividad.fechaInscripcion,
    asistenciaConfirmada: this.inscripcionActividad.asistenciaConfirmada,

    // Mapeo exacto al DTO de Java
    estadoId: Number(this.inscripcionActividad.estadoId),
    usuarioId: Number(this.inscripcionActividad.usuarioId),
    actividadId: Number(this.inscripcionActividad.actividadId),

  };
  console.log(JSON.stringify(inscripcionActividadToSend, null, 2)); 
  const request$ = this.inscripcionActividad.idInscripcion > 0 
    ? this.inscripcionActividadService.editarInscripcion(inscripcionActividadToSend) 
    : this.inscripcionActividadService.guardarInscripcion(inscripcionActividadToSend);

  request$.subscribe({
    next: () => {
      Swal.fire("Éxito", "Operación realizada correctamente", "success");
      this.router.navigate(['/app/inscripciones-actividades']);
    },
    error: (err) => {
      const mensaje = err.error?.mensaje || 'Error inesperado';
      Swal.fire("Error", mensaje, "error");
      this.sharingDataService.errorsInscripcionActividadFormEventEmitter.emit(err);
    }
  });
}


  

  cargarActividades(): void{
    this.actividadService.listadoActividadesCompletas().subscribe(
      data => {
        this.actividades = data;
        console.log("Datos recibidos de actividades:", data)
      }
    )
  }




}
