import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Usuario } from '../../domain/Usuario';
import { LoginRequest } from '../../domain/LoginRequest';
import { SharingDataServiceUsuario } from '../../sharing-data-service/sharing-data-service-usuario';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-auth',
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {

  usuario!: Usuario;
  loginRequest: LoginRequest = {
    nombreUsuario: '',
    contrasenia: ''
  };

  constructor(private sharingDataService: SharingDataServiceUsuario, private authService: AuthService, private router: Router){
    
  }

  onSubmit() {
    if (!this.loginRequest.nombreUsuario || !this.loginRequest.contrasenia) {
      Swal.fire('Error de validación', 'Usuario o Contraseña requeridos', 'error');
      return; 
    }

    this.authService.loginUsuario({ 
      nombreUsuario: this.loginRequest.nombreUsuario, 
      contrasenia: this.loginRequest.contrasenia 
    }).subscribe({
      next: (response: { token:any; } ) => {
        console.log('LOGIN RESPONSE', response);
        const token = response.token;
        const payload = this.authService.getPayload(token);
        
       this.authService.token = token;

        //Guardar token en local storage
        this.authService.usuario = { 
          usuario: { nombreUsuario: payload.sub },
          isAuth: true,
          isAdmin: payload.isAdmin == true,
          isAsociado: payload.isAsociado == true
       };
                                                  
        this.router.navigate(['/app/home']);
      }, 
      error: error => {
        if (error.status === 401) {
          Swal.fire('Error en el Login', error.error.message, 'error');
      } else {
          Swal.fire('Error', 'Error inesperado', 'error');
          console.error(error);
        }
      }
    });
  }


}
