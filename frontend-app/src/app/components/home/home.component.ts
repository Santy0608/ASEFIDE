import { Component, Input, OnInit } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { Actividad } from '../../domain/Actividad';
import { Beneficio } from '../../domain/Beneficio';
import { Transaccion } from '../../domain/Transaccion';
import { Estado } from '../../domain/Estado';
import { AhorroComponent } from '../ahorro/ahorro.component';
import { CuentasAhorro } from '../../domain/CuentasAhorro';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Aporte } from '../../domain/Aporte';
import { UsuarioService } from '../../services/usuario.service';
import { M } from '@angular/cdk/keycodes';
import { AhorroUsuario } from '../../domain/AhorroUsuario';
import { TransaccionUsuario } from '../../domain/TransaccionUsuario';
import { PrestamoUsuario } from '../../domain/PrestamoUsuario';
import { InscripcionUsuario } from '../../domain/InscripcionUsuario';
import { AporteAsociado } from '../../domain/AporteAsociado';

@Component({
  selector: 'app-home',
  imports: [RouterModule, RouterLink, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{

    aportes: AporteAsociado[] = [];
    ahorrosUsuarios: AhorroUsuario[] = [];
    transaccionesUsuarios: TransaccionUsuario[] = [];
    prestamosUsuarios: PrestamoUsuario[] = [];
    inscripcionesActividadesUsuarios: InscripcionUsuario[] = [];

    constructor(private router: Router, public authService: AuthService, private usuarioService: UsuarioService){
      
    }

    @Input() actividades: Actividad[] = [];
    @Input() beneficios: Beneficio[] = [];
    @Input() transacciones: Transaccion[] = [];
    @Input() estados: Estado[] = [];
    @Input() ahorros: CuentasAhorro[] = [];


    hoy = new Date();

  modules = [
    { title: 'Dashboard',     desc: 'Visualice metricas en base al dashboard de la asociación',               route: '/app/dashboard',       img: 'assets/images/Dashboard.png',     tag: 'Visualización', color: 'col-blue'},
    { title: 'Usuarios',      desc: 'Control claro de todas las personas vinculadas a la asociación.',        route: '/app/usuarios',        img: 'assets/images/Usuarios.png',      tag: 'Gestión',   color: 'col-blue'   },
    { title: 'Beneficios',    desc: 'Registre los beneficios que ofrece la asociación a sus miembros.',       route: '/app/beneficios',      img: 'assets/images/Beneficios.png',    tag: 'Membresía', color: 'col-teal'   },
    { title: 'Servicios',     desc: 'Disponibilidad y uso de servicios por parte de los asociados.',          route: '/app/servicios',       img: 'assets/images/Servicios.png',     tag: 'Recursos',  color: 'col-amber'  },
    { title: 'Transacciones', desc: 'Control de movimientos y aportes financieros de los asociados.',         route: '/app/transacciones',   img: 'assets/images/Transacciones.png', tag: 'Finanzas',  color: 'col-purple' },
    { title: 'Ahorros',       desc: 'Aportes voluntarios y seguimiento de cuentas de ahorro.',                route: '/app/cuentas-ahorros', img: 'assets/images/Ahorros.png',       tag: 'Finanzas',  color: 'col-green'  },
    { title: 'Actividades',   desc: 'Gestione actividades para fomentar la participación asociativa.',        route: '/app/actividades',     img: 'assets/images/Actividades.png',   tag: 'Eventos',   color: 'col-coral'  },
    { title: 'Préstamos',     desc: 'Montos, plazos y estados de pago de los asociados.',                     route: '/app/prestamos',       img: 'assets/images/Prestamos.png',     tag: 'Finanzas',  color: 'col-pink'   },
    { title: 'Reportes',      desc: 'Visualice informes y métricas clave de la asociación.',                  route: '/app/reportes',        img: 'assets/images/Reportes.png',      tag: 'Análisis',  color: 'col-red'    },
    { title: 'Estados',       desc: 'Catálogo general de estados para los registros de la asociación.',       route: '/app/estados',         img: 'assets/images/Estados.png',       tag: 'Catálogo',  color: 'col-gray'   },
  ];


  get login(){
    return this.authService.usuario;
  }

  get admin(): boolean {
    return this.login?.isAdmin ?? false;
  }

  get asociado(): boolean {
    return !this.admin && (this.login?.isAsociado ?? false);
  }



  nombreUsuario: string = '';
  tabActivo: 'aportes' | 'ahorros' | 'transacciones' | 'prestamos' | 'inscripciones' = 'aportes';
 
  paginaActual: number = 1;
  itemsPorPagina: number = 6;
  paginas: number[] = [1, 2, 3];
 

  ahorro: any[] = [
    { cuenta: 'AH-00001', tipo: 'Ahorro Corriente', saldo: 150000, fechaApertura: new Date('2022-01-15'), estado: 'Activa' },
    { cuenta: 'AH-00002', tipo: 'Ahorro a Plazo', saldo: 500000, fechaApertura: new Date('2021-06-01'), estado: 'Activa' },
    { cuenta: 'AH-00003', tipo: 'Ahorro Navideño', saldo: 75000, fechaApertura: new Date('2023-03-10'), estado: 'Inactiva' },
  ];
 
 
  ngOnInit(): void {
    this.nombreUsuario = this.authService.usuario?.nombreUsuario ?? 'Asociado';
    this.cargarAportes();
    this.cargarAhorros();
    this.cargarTransacciones();
    this.cargarPrestamos();
    this.cargarInscripcionesActividadesUsuarios();
  }
 
  get aportesPaginados(): any[] {
    const inicio = (this.paginaActual - 1) * this.itemsPorPagina;
    return this.aportes.slice(inicio, inicio + this.itemsPorPagina);
  }
 
  irPagina(p: number): void {
    this.paginaActual = p;
  }
 
  siguiente(): void {
    if (this.paginaActual < this.paginas.length) {
      this.paginaActual++;
    }
  }

  cargarAportes(): void {
    this.usuarioService.obtenerMisAportes().subscribe({
      next: (data) => {
        console.log('Aportes raw:', data);
        console.log('Primer aporte:', data[0]);
        this.aportes = data;
      },
      error: (err) => console.error(err)
    });
  }

  cargarAhorros(): void {
    this.usuarioService.obtenerMisAhorros().subscribe({
      next: (data) => {
        this.ahorrosUsuarios = data;
      },
      error: (err) => console.error(err)
    });
  }

  cargarTransacciones(): void{
    this.usuarioService.obtenerMisTransacciones().subscribe({
      next: (data) => {
        this.transaccionesUsuarios = data;
      }, 
      error: (err) => console.error(err)
    })
  }

  cargarPrestamos(): void{
    this.usuarioService.obtenerMisPrestamos().subscribe({
      next: (data) => {
        this.prestamosUsuarios = data;
      },
      error: (err) => console.error(err)
    });
  }

  cargarInscripcionesActividadesUsuarios(): void{
    this.usuarioService.obtenerMisInscripcionesActividades().subscribe({
      next: (data) => {
        this.inscripcionesActividadesUsuarios = data;
      },
      error: (err) => console.error(err)
    });
  }

  descargarPDF(): void{
    this.usuarioService.descargarAportePdf().subscribe({
        next: (res) => {
            const file = new Blob([res], { type: 'application/pdf' });
            const fileURL = URL.createObjectURL(file);
            window.open(fileURL); 
        },
        error: (err) => {
            console.error('Error al obtener el reporte', err);
            // Aquí podrías mostrar una alerta al usuario
        }
    });
  }

}
