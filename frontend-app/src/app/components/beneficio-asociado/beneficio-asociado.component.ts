import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Beneficio } from '../../domain/Beneficio';
import { BeneficioService } from '../../services/beneficio.service';

@Component({
  selector: 'app-beneficio-asociado',
  imports: [RouterModule, CommonModule],
  templateUrl: './beneficio-asociado.component.html'
})
export class BeneficioAsociadoComponent implements OnInit{

  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute, private beneficioService: BeneficioService){

  }

  beneficios: Beneficio[] = [];
  cargando: boolean = true;
  
  ngOnInit(): void {
    this.listadoBeneficios();
  }
 

  verDetalle(beneficio: Beneficio): void {
    // this.router.navigate(['/app/beneficios', beneficio.id]);
    console.log('Ver beneficio:', beneficio);
  }

  listadoBeneficios(): void {
  this.beneficioService.listadoBeneficiosAsociados().subscribe(
    data => {
      this.beneficios = data;
      this.cargando = false; 
    },
    error => {
      console.error('Error al cargar beneficios', error);
      this.cargando = false; 
    }
  );
}




}

