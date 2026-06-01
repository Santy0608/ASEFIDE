import { Component, OnInit } from '@angular/core';
import { CuentasAhorro } from '../../domain/CuentasAhorro';
import { TipoAhorro } from '../../domain/TipoAhorro';
import { Usuario } from '../../domain/Usuario';
import { UsuarioService } from '../../services/usuario.service';
import { SharingDataServiceCuentasAhorro } from '../../sharing-data-service/sharing-data-service-cuentas-ahorro';
import { AhorroService } from '../../services/ahorro.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { TipoAhorroService } from '../../services/tipo-ahorro.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-ahorro-form',
  imports: [RouterModule, RouterLink, CommonModule, NgSelectModule, FormsModule],
  templateUrl: './ahorro-form.component.html',
})
export class AhorroFormComponent implements OnInit{

  errors: any;
  cuentaAhorro!: CuentasAhorro
  cuentasAhorro!: CuentasAhorro;
  tiposAhorros: TipoAhorro[] = [];
  usuarios: Usuario[] = [];


  constructor(private usuarioService: UsuarioService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServiceCuentasAhorro,
              private cuentasAhorroService: AhorroService,
              private tipoAhorroService: TipoAhorroService
  ){
    this.cuentaAhorro = new CuentasAhorro();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsCuentasAhorroFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectCuentasAhorroEventEmitter.subscribe(cuentaAhorro => this.cuentaAhorro = cuentaAhorro);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idAhorro') || '0');
      if (id > 0){
        this.cuentasAhorroService.buscarAhorroPorId(id).subscribe(cuentaAhorro => this.cuentaAhorro = cuentaAhorro);
      }
    })

    this.cargarUsuarios();
    this.cargarTiposAhorros();
  }

  cargarUsuarios(): void{
    this.usuarioService.listarUsuariosCompletos().subscribe(data => {
      this.usuarios = data.map(u => ({
        ...u, 
        nombreCompleto: `${u.nombre} ${u.apellidoPaterno}` 
      }));
    });   
  }

  
  onSubmit(cuentaAhorroForm: NgForm): void {
  if (cuentaAhorroForm.invalid) return;

  // Aplanamos el objeto para el backend
  const cuentaAhorroToSend = {
    idAhorro: this.cuentaAhorro.idAhorro,
    montoAporte: this.cuentaAhorro.montoAporte,
    fechaApertura: this.cuentaAhorro.fechaApertura,
     saldoActual: this.cuentaAhorro.idAhorro > 0
        ? this.cuentaAhorro.saldoActual   
        : this.cuentaAhorro.montoAporte,  
    // Mapeo exacto al DTO de Java
    estadoId: Number(this.cuentaAhorro.estadoId),
    tipoAhorroId: Number(this.cuentaAhorro.tipoAhorroId),
    usuarioId: Number(this.cuentaAhorro.usuarioId)
  };
  console.log(JSON.stringify(cuentaAhorroToSend, null, 2)); 
  const request$ = this.cuentaAhorro.idAhorro > 0 
    ? this.cuentasAhorroService.editarCuentaAhorro(cuentaAhorroToSend) 
    : this.cuentasAhorroService.guardarCuentaAhorro(cuentaAhorroToSend);

  request$.subscribe({
    next: () => {
      Swal.fire("Éxito", "Operación realizada correctamente", "success");
      this.router.navigate(['/app/cuentas-ahorros']);
    },
    error: (err) => {
      const mensaje = err.error?.mensaje || 'Error inesperado';
      Swal.fire("Error", mensaje, "error");
      this.sharingDataService.errorsCuentasAhorroFormEventEmitter.emit(err);
    }
  });
}


  

    cargarTiposAhorros(): void{
      this.tipoAhorroService.listadoTiposAhorros().subscribe(
        data => {
          this.tiposAhorros = data;
          console.log("Datos recibidos de tipos de ahorros:", data)
        }
      )
    }

    

}
