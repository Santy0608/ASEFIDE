import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TransaccionService } from '../../services/transaccion.service';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { Observable, switchMap } from 'rxjs';
import { Transaccion } from '../../domain/Transaccion';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceUsuario } from '../../sharing-data-service/sharing-data-service-usuario';
import { SharingDataServiceTransaccion } from '../../sharing-data-service/sharing-data-service-transaccion';
import { Usuario } from '../../domain/Usuario';
import { AhorroService } from '../../services/ahorro.service';
import { D } from '@angular/cdk/keycodes';

@Component({
  selector: 'app-transaccion',
  imports: [CommonModule, RouterModule, RouterLink, PaginatorComponent],
  templateUrl: './transaccion.component.html',
})
export class TransaccionComponent implements OnInit{

  transacciones: Transaccion[] = [];
  paginator: any = {};
  cantidadTransacciones: number = 0;
  usuario!: Usuario;
  transaccion!: Transaccion;
  modalVisible = false;
  usuarioSeleccionado: any = null;
  top5Transacciones: Transaccion[] = [];

  modalHistorialVisible: boolean = false;
  historialTransacciones: Transaccion[] = [];
  cargandoHistorial: boolean = false;

  sumaTotal: number = 0;
  promedio: number = 0;

  constructor(private transaccionService: TransaccionService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceTransaccion, private ahorroService: AhorroService){
    if (this.router.getCurrentNavigation()?.extras.state){
      this.transacciones = this.router.getCurrentNavigation()?.extras.state!['transacciones'];
      this.paginator = this.router.getCurrentNavigation()?.extras.state!['paginator'];
    }
  }

  ngOnInit(): void {
    this.listadoTransaccionesCompletos();
    this.cargarMetricas();
    this.cargarTop5();
  }

 
  cargarMetricas(): void {
    this.ahorroService.getTotalAhorros().subscribe({
      next: (data) => this.sumaTotal = data,
      error: (err) => console.error(err)
    });

    this.ahorroService.getPromedioAhorros().subscribe({
      next: (data) => this.promedio = data,
      error: (err) => console.error(err)
    });
  }

  cargarTop5(): void {
    this.transaccionService.top5Transacciones().subscribe({
      next: (data) => this.top5Transacciones = data,
      error: (err) => console.error(err)
    });
  }

  historialTransaccionesVM(): void{
    this.transaccionService.historialTransaccionesVM().subscribe(
      data => {
        this.transacciones = data;
      }
    )
  }


  cerrarModal(): void {
    this.modalVisible = false;
    this.usuarioSeleccionado = null;
  }
  
  cerrarModalHistorial(): void{
    this.modalHistorialVisible = false;
    this.historialTransacciones = [];
    this.usuarioSeleccionado = null;
  }

  getInitials(usuario: any): string {
    if (!usuario?.nombreCompleto) return '';
    const partes = usuario.nombreCompleto.split(' ');
    return (partes[0][0] + (partes[1]?.[0] || '')).toUpperCase();
  }

  cargarCantidad(transaccion: any): void {
    console.log('TRANSACCION:', transaccion);
    console.log('ID:', transaccion.usuarioId); 
    const usuarioId = Number(transaccion.usuarioId);

    this.usuarioSeleccionado = {
      nombreCompleto: transaccion.nombreUsuario + ' ' + transaccion.apellidoPaterno,
      idUsuario: usuarioId
    };

    this.modalVisible = true;

    this.transaccionService.getCantidadTransacciones(usuarioId)
      .subscribe({
        next: (data: number) => {
          this.cantidadTransacciones = data;
        }
      });
  }

  cargarHistorial(transaccion: any): void {
    this.usuarioSeleccionado = transaccion;
    this.cargandoHistorial = true;
    this.modalHistorialVisible = true;

    this.transaccionService.historialTransacciones(transaccion.usuarioId).subscribe({
      next: (data) => {
        this.historialTransacciones = data;
        this.cargandoHistorial = false;
      },
      error: (err) => {
        console.error(err);
        this.cargandoHistorial = false;
      }
    });
  }

  listadoTransacciones(): void{
    this.route.paramMap.pipe(
    switchMap(params => {
    const page = +(params.get('page') || '0');
      return this.transaccionService.listadoTransaccionesPaginacion(page);
    })
    ).subscribe(pageable => {
      this.transacciones = pageable.content as Transaccion[];
      this.paginator = pageable;
    });
  }

  listadoTransaccionesCompletos(): void{
    this.transaccionService.listadoTransaccionesCompletos().subscribe(
      data => {
        this.transacciones = data;
      }
    )
  }

  pageUsuariosEvent(): void{
    this.sharingDataService.pageUsuariosEventEmitter.subscribe(pageable => {
      this.transacciones = pageable.usuarios;
      this.paginator = pageable.paginator;
    })
  }



  OnSelectedTransaccion(transaccion: Transaccion): void{
    this.router.navigate(['/app/transacciones/editar-transaccion', transaccion.idTransaccion])
  }
    
    
  eliminarTransaccion(idTransaccion: number): void {
    
        Swal.fire({
          title: "¿Eliminar transacción?",
          text: "La transacción se marcará como inactiva.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#d33",
          cancelButtonColor: "#3085d6",
          confirmButtonText: "Sí, eliminar",
          cancelButtonText: "Cancelar"
        }).then((result) => {
    
          if (result.isConfirmed) {
    
            this.transaccionService.eliminarTransaccion(idTransaccion).subscribe({
    
              next: () => {
    
                Swal.fire(
                  "Eliminado",
                  "La transacción fue eliminada correctamente",
                  "success"
                );
    
                // quitar del listado sin recargar
               this.listadoTransacciones();
    
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
