import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../domain/Usuario';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServiceUsuario } from '../../sharing-data-service/sharing-data-service-usuario';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { Telefono } from '../../domain/Telefono';
import { Correo } from '../../domain/Correo';
import { DatosAsociados } from '../../domain/DatosAsociados';
import { TelefonoService } from '../../services/telefono.service';
import { CorreoService } from '../../services/correo.service';
import { DireccionService } from '../../services/direccion.service';
import { DatosAsociadosService } from '../../services/datos-asociados.service';
import { Direccion } from '../../domain/Direccion';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-usuario-form',
  imports: [FormsModule, CommonModule, RouterModule, NgSelectModule],
  templateUrl: './usuario-form.component.html',
  styleUrl: './usuario-form.component.css'
})
export class UsuarioFormComponent implements OnInit{

  errors: any;
  usuario!: Usuario;
  telefonos: Telefono[] = [];
  correos: Correo[] = [];
  datosAsociados: DatosAsociados[] = [];
  direcciones: Direccion[] = [];

  constructor(private usuarioService: UsuarioService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServiceUsuario,
              private telefonoService: TelefonoService,
              private correoService: CorreoService,
              private direccionService: DireccionService,
              private datosAsociadosService: DatosAsociadosService
  ){
    this.usuario = new Usuario();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsUsuariosFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectUsuarioEventEmitter.subscribe(usuario => this.usuario = usuario);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idUsuario') || '0');
      if (id > 0){
        this.usuarioService.buscarUsuarioPorId(id).subscribe(usuario => this.usuario = usuario);
      }
    })

    this.cargarTelefonos();
    this.cargarCorreos();
    this.cargarDirecciones();
    this.cargarDatosAsociados();
  }

  
  onSubmit(usuarioForm: NgForm): void {
  if (usuarioForm.invalid) return;

  // Aplanamos el objeto para el backend
  const usuarioToSend = {
    idUsuario: this.usuario.idUsuario,
    identificacion: this.usuario.identificacion,
    nombre: this.usuario.nombre,
    apellidoPaterno: this.usuario.apellidoPaterno,
    apellidoMaterno: this.usuario.apellidoMaterno,
    nombreUsuario: this.usuario.nombreUsuario,
    contrasenia: this.usuario.contrasenia,
    
    correosIds: this.usuario.correosIds, 
    numerosIds: this.usuario.numerosIds,
    direccionId: Number(this.usuario.direccionId),
    identificacionDatosAsociados: Number(this.usuario.identificacionDatosAsociados)
  };
  console.log(JSON.stringify(usuarioToSend, null, 2)); 
  const request$ = this.usuario.idUsuario > 0 
    ? this.usuarioService.editarUsuario(usuarioToSend) 
    : this.usuarioService.guardarUsuario(usuarioToSend);

  request$.subscribe({
    next: () => {
      Swal.fire("Éxito", "Operación realizada correctamente", "success");
      this.router.navigate(['/app/usuarios']);
    },
    error: (err) => {
      const mensaje = err.error?.mensaje || 'Error inesperado';
      Swal.fire("Error", mensaje, "error");
      this.sharingDataService.errorsUsuariosFormEventEmitter.emit(err);
    }
  });
}


    cargarTelefonos(): void{
      this.telefonoService.listadoTelefonosCompletos().subscribe(
        data => {
          this.telefonos = data;
          console.log("Datos recibidos de teléfonos:", data); 
        }
      )
    }

    cargarCorreos(): void{
      this.correoService.listadoCorreosCompletos().subscribe(
        data => {
          this.correos = data;
          console.log("Datos recibidos de correos:", data)
        }
      )
    }

    cargarDirecciones(): void{
      this.direccionService.listadoDireccionesCompletas().subscribe(
        data => {
          this.direcciones = data;
          console.log("Datos recibidos de direcciones", data);
        }
      )
    }

    cargarDatosAsociados(): void{
      this.datosAsociadosService.listadoDatosAsociadosCompletos().subscribe(
        data => {
          this.datosAsociados = data;
          console.log("Datos recibidos de datos asociados", data);
        }
      )
    }


}
