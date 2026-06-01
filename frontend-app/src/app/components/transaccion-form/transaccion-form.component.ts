import { Component, OnInit } from '@angular/core';
import { Transaccion } from '../../domain/Transaccion';
import { Usuario } from '../../domain/Usuario';
import { TipoTransaccion } from '../../domain/TipoTransaccion';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { SharingDataServiceTransaccion } from '../../sharing-data-service/sharing-data-service-transaccion';
import { TipoTransaccionService } from '../../services/tipo-transaccion.service';
import { TransaccionService } from '../../services/transaccion.service';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-transaccion-form',
  imports: [FormsModule, CommonModule, NgSelectModule, RouterModule, RouterLink],
  templateUrl: './transaccion-form.component.html',
})
export class TransaccionFormComponent implements OnInit{

  errors: any;
  transaccion!: Transaccion;
  tiposTransacciones: TipoTransaccion[] = [];
  usuarios: Usuario[] = [];


  constructor(private usuarioService: UsuarioService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServiceTransaccion,
              private tipoTransaccionService: TipoTransaccionService,
              private transaccionService: TransaccionService
  ){
    this.transaccion = new Transaccion();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsTransaccionFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectTransaccionEventEmitter.subscribe(transaccion => this.transaccion = transaccion);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idTransaccion') || '0');
      if (id > 0){
        this.transaccionService.buscarTransaccionPorId(id).subscribe(transaccion => this.transaccion = transaccion);
      }
    })

    this.cargarUsuarios();
    this.cargarTiposTransacciones();
  }

  cargarUsuarios(): void{
    this.usuarioService.listarUsuariosCompletos().subscribe(data => {
      this.usuarios = data.map(u => ({
        ...u, 
        nombreCompleto: `${u.nombre} ${u.apellidoPaterno}` 
      }));
    });   
  }

  
  onSubmit(transaccionForm: NgForm): void {
  if (transaccionForm.invalid) return;

  // Aplanamos el objeto para el backend
  const transaccionToSend = {
    idTransaccion: this.transaccion.idTransaccion,
    fechaTransaccion: this.transaccion.fechaTransaccion,
    montoTotal: this.transaccion.montoTotal,

    // Mapeo exacto al DTO de Java
    estadoId: Number(this.transaccion.estadoId),
    usuarioId: Number(this.transaccion.usuarioId),
    tipoTransaccionId: Number(this.transaccion.tipoTransaccionId)
  };
  console.log(JSON.stringify(transaccionToSend, null, 2)); 
  const request$ = this.transaccion.idTransaccion > 0 
    ? this.transaccionService.editarTransaccion(transaccionToSend) 
    : this.transaccionService.guardarTransaccion(transaccionToSend);

  request$.subscribe({
    next: () => {
      Swal.fire("Éxito", "Operación realizada correctamente", "success");
      this.router.navigate(['/app/transacciones']);
    },
    error: (err) => {
      const mensaje = err.error?.mensaje || 'Error inesperado';
      Swal.fire("Error", mensaje, "error");
      this.sharingDataService.errorsTransaccionFormEventEmitter.emit(err);
    }
  });
}


  

  cargarTiposTransacciones(): void{
    this.tipoTransaccionService.listadoTiposTransaccionesCompletas().subscribe(
      data => {
        this.tiposTransacciones = data;
        console.log("Datos recibidos de tipos de transacciones:", data)
      }
    )
  }



}
