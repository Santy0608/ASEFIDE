import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../domain/Usuario';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { switchMap } from 'rxjs';
import { SharingDataServiceUsuario } from '../../sharing-data-service/sharing-data-service-usuario';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-usuario',
  imports: [RouterModule, CommonModule, PaginatorComponent, FormsModule],
  templateUrl: './usuario.component.html',
  styleUrl: './usuario.component.css'
})
export class UsuarioComponent implements OnInit{

  usuarios: Usuario[] = [];
  paginator: any = {};
  errors: any;
  nombreBuscar: string = '';
  estaBuscando: boolean = false;
  usuariosOriginal: Usuario[] = [];
  usuariosNuevosMes: Usuario[] = [];
  ordenActual: 'default' | 'alfabetico' | 'fecha' = 'default';

  totalActivos: number = 0;
  totalInactivos: number = 0;

  modalInactivosVisible: boolean = false;
  usuariosInactivos: Usuario[] = [];
  cargandoInactivos: boolean = false;

  verUsuariosInactivos(): void {
  this.modalInactivosVisible = true;
  this.cargandoInactivos = true;

    this.usuarioService.listadoUsuariosInactivos().subscribe({
      next: (data) => {
        this.usuariosInactivos = data ?? [];
        this.cargandoInactivos = false;
      },
      error: (err) => {
        console.error(err);
        this.cargandoInactivos = false;
      }
    });
  }

  cerrarModalInactivos(): void {
    this.modalInactivosVisible = false;
    this.usuariosInactivos = [];
  }

  getInitials(nombre: string, apellido: string): string {
    return ((nombre?.[0] || '') + (apellido?.[0] || '')).toUpperCase();
  }

  constructor(private usuarioService: UsuarioService, private router: Router, private route: ActivatedRoute, private sharingDataServiceUsUsuario: SharingDataServiceUsuario){
    if (this.router.getCurrentNavigation()?.extras.state){
      this.usuarios = this.router.getCurrentNavigation()?.extras.state!['usuarios'];
      this.paginator = this.router.getCurrentNavigation()?.extras.state!['paginator'];
    }
  }


  cargarEstados(): void {
    this.usuarioService.usuariosPorEstado().subscribe({
      next: (data) => {
        data.forEach(e => {
          if (e.nombre === 'ACTIVO')   this.totalActivos   = e.cantidadUsuarios;
          if (e.nombre === 'INACTIVO') this.totalInactivos = e.cantidadUsuarios;
        });
      },
      error: (err) => console.error(err)
    });
  }


  cargarUltimoMes(): void {
    this.usuarioService.usuariosUltimoMes().subscribe({
      next: (data) => this.usuariosNuevosMes = data,
      error: (err) => console.error(err)
    });
  }

   // Helper para el badge en la tabla
  esNuevoEsteMes(idUsuario: number): boolean {
    return this.usuariosNuevosMes?.some(u => u.idUsuario === idUsuario) ?? false;
  }

 
  ordenarDefault(): void {
    this.ordenActual = 'default';
    //this.usuarios = [...this.usuariosOriginal];
    this.usuarioService.listarUsuariosCompletos().subscribe(
      data => {
        this.usuarios = data;
      }
    )
  }

  ordenarAlfabetico(): void {
    this.ordenActual = 'alfabetico';
    this.usuarioService.ordenarAlfabetico().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error(err)
    });
  }

  ordenarPorFecha(): void {
    this.ordenActual = 'fecha';
    this.usuarioService.ordenarPorFecha().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error(err)
    });
  }

  ngOnInit(): void {
    this.listadoUsuariosCompletos();
    this.cargarEstados();
    this.cargarUltimoMes();
  }

  buscarUsuario(): void {
    if (this.nombreBuscar.trim() === '') {
      this.listadoUsuarios(); // Si el buscador está vacío, vuelve a la lista completa
      this.estaBuscando = false;
      return;
    }

    this.usuarioService.buscarPorNombre(this.nombreBuscar).subscribe({
      next: (resultado) => {
 
        this.usuarios = resultado; 
        this.estaBuscando = true;
        
        console.log(`Se encontraron ${resultado.length} usuarios`);
      },
      error: (err) => {
        console.error('Error:', err);
      }
    });
  }

  listadoUsuarios(): void {
    this.route.paramMap.pipe(
    switchMap(params => {
      const page = +(params.get('page') || '0');
      return this.usuarioService.listadoUsuariosPaginacion(page);
    })
    ).subscribe(pageable => {
      this.usuarios = pageable.content as Usuario[];
      this.usuariosOriginal = [...this.usuarios];
      this.paginator = pageable;
    });
  }

  listadoUsuariosCompletos(): void{
    this.usuarioService.listarUsuariosCompletos().subscribe(
      data => {
        this.usuarios = data;
      }
    )
  }

  limpiarBusqueda() {
    this.nombreBuscar = '';
    this.listadoUsuariosCompletos();
  }

   pageEmployeesEvent(): void{
    this.sharingDataServiceUsUsuario.pageUsuariosEventEmitter.subscribe(pageable => {
      this.usuarios = pageable.usuarios;
      this.paginator = pageable.paginator;
    })
  }

  OnSelectedUsuario(usuario: Usuario): void{
    this.router.navigate(['/app/usuarios/editar-usuario', usuario.idUsuario]);
  }
  
  
    eliminarUsuario(idUsuario: number): void {
  
      Swal.fire({
        title: "¿Eliminar usuario?",
        text: "El usuario se marcará como inactivo.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
  
        if (result.isConfirmed) {
  
          this.usuarioService.eliminarUsuario(idUsuario).subscribe({
  
            next: () => {
  
              Swal.fire(
                "Eliminado",
                "El usuario fue eliminado correctamente",
                "success"
              );
  
              // quitar del listado sin recargar
             this.listadoUsuarios();
  
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
