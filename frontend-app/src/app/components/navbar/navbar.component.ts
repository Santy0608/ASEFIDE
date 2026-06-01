import { Component, HostListener, Input } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { Estado } from '../../domain/Estado';
import { Actividad } from '../../domain/Actividad';
import { Beneficio } from '../../domain/Beneficio';
import { Servicio } from '../../domain/servicio';
import { Transaccion } from '../../domain/Transaccion';
import { CuentasAhorro } from '../../domain/CuentasAhorro';
import { Prestamo } from '../../domain/Prestamo';
import { Usuario } from '../../domain/Usuario';
import { Reporte } from '../../domain/Reporte';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, RouterLink, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  constructor(private router: Router, private authService: AuthService){

  }

  @Input() estados: Estado[] = [];
  @Input() actividades: Actividad[] =  [];
  @Input() beneficios: Beneficio[] = [];
  @Input() servicios: Servicio[] = [];
  @Input() transacciones: Transaccion[] = [];
  @Input() cuentasAhorro: CuentasAhorro[] = [];
  @Input() prestamos: Prestamo[] = [];
  @Input() usuarios: Usuario[] = [];
  @Input() reportes: Reporte[] = [];
  @Input() ahorros: CuentasAhorro[] = [];


 userMenuOpen: boolean = false;
 dropdownOpen: string | null = null;

  toggleDropdown(name: string, event: MouseEvent) {
    event.stopPropagation();
    this.dropdownOpen = this.dropdownOpen === name ? null : name;
  }

  closeDropdown() {
    this.dropdownOpen = null;
  }

  @HostListener('document:click')
  onDocumentClick(...args: []): void {
    this.userMenuOpen = false;
  }

   handlerLogout(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

   get login(){
    return this.authService.usuario;
  }

  get admin(): boolean {
    return this.login?.isAdmin ?? false;
  }

  get asociado(): boolean {
    return !this.admin && (this.login?.isAsociado ?? false);
  }

  toggleUserMenu(event: MouseEvent): void {
    event.stopPropagation();
    this.userMenuOpen = !this.userMenuOpen;
  }

}
