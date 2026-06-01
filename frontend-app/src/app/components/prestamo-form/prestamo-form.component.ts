import { Component, OnInit } from '@angular/core';
import { Prestamo } from '../../domain/Prestamo';
import { Usuario } from '../../domain/Usuario';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { SharingDataServicePrestamo } from '../../sharing-data-service/sharing-data-service-prestamo';
import { PrestamoService } from '../../services/prestamo.service';
import { FormsModule, NgForm } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-prestamo-form',
  imports: [NgSelectModule, FormsModule, RouterModule, CommonModule, RouterLink],
  templateUrl: './prestamo-form.component.html',
})
export class PrestamoFormComponent implements OnInit{

  errors: any;
  prestamo!: Prestamo;
  prestamos: Prestamo[] = [];
  usuarios: Usuario[] = [];
  

  constructor(private usuarioService: UsuarioService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServicePrestamo,
              private prestamoService: PrestamoService
  ){
    this.prestamo = new Prestamo();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsPrestamoFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectPrestamoEventEmitter.subscribe(prestamo => this.prestamo = prestamo);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idPrestamo') || '0');
      if (id > 0){
        this.prestamoService.buscarPrestamoPorId(id).subscribe(prestamo => this.prestamo = prestamo);
      }
    })

    this.cargarUsuarios();
  }

  cargarUsuarios(): void{
    this.usuarioService.listarUsuariosCompletos().subscribe(data => {
      this.usuarios = data.map(u => ({
        ...u, 
        nombreCompleto: `${u.nombre} ${u.apellidoPaterno}` 
      }));
    });   
  }

  
  onSubmit(prestamoForm: NgForm): void {
  if (prestamoForm.invalid) return;

  // Aplanamos el objeto para el backend
  const prestamoToSend = {
    idPrestamo: this.prestamo.idPrestamo,
    montoSolicitado: this.prestamo.montoSolicitado,
    fechaAprobacion: this.prestamo.fechaAprobacion,
    saldoPendiente: this.prestamo.idPrestamo > 0
        ? this.prestamo.saldoPendiente   
        : this.prestamo.montoSolicitado,
    tasaIntereses: this.prestamo.tasaIntereses,
    plazoMeses: this.prestamo.plazoMeses,
  
    // Mapeo exacto al DTO de Java
    estadoId: Number(this.prestamo.estadoId),
    usuarioId: Number(this.prestamo.usuarioId),
  };
  console.log(JSON.stringify(prestamoToSend, null, 2)); 
  const request$ = this.prestamo.idPrestamo > 0 
    ? this.prestamoService.editarPrestamo(prestamoToSend) 
    : this.prestamoService.guardarPrestamo(prestamoToSend);

  request$.subscribe({
    next: () => {
      Swal.fire("Éxito", "Operación realizada correctamente", "success");
      this.router.navigate(['/app/prestamos']);
    },
    error: (err) => {
      const mensaje = err.error?.mensaje || 'Error inesperado';
      Swal.fire("Error", mensaje, "error");
      this.sharingDataService.errorsPrestamoFormEventEmitter.emit(err);
    }
  });
}




}
