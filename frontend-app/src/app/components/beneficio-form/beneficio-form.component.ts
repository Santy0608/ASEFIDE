import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { Beneficio } from '../../domain/Beneficio';
import { Categoria } from '../../domain/Categoria';
import { BeneficioService } from '../../services/beneficio.service';
import { SharingDataServiceBeneficio } from '../../sharing-data-service/sharing-data-service-beneficio';
import { CategoriaService } from '../../services/categoria.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-beneficio-form',
  imports: [FormsModule, CommonModule, NgSelectModule, RouterModule],
  templateUrl: './beneficio-form.component.html',
})
export class BeneficioFormComponent implements OnInit{

  
  errors: any;
  beneficio!: Beneficio;
  categorias: Categoria[] = [];


  constructor(private beneficioService: BeneficioService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServiceBeneficio,
              private categoriaService: CategoriaService
  ){
    this.beneficio = new Beneficio();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsBeneficioFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectBeneficioEventEmitter.subscribe(beneficio => this.beneficio = beneficio);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idBeneficio') || '0');
      if (id > 0){
        this.beneficioService.buscarBeneficioPorId(id).subscribe(beneficio => this.beneficio = beneficio);
      }
    })

    this.cargarCategorias();
  }

  

  
  onSubmit(beneficioForm: NgForm): void {
  if (beneficioForm.invalid) return;

  // Aplanamos el objeto para el backend
  const beneficioToSend = {
    idBeneficio: this.beneficio.idBeneficio,
    nombreBeneficio: this.beneficio.nombreBeneficio,
    descripcion: this.beneficio.descripcion,
  
    // Mapeo exacto al DTO de Java
    estadoId: Number(this.beneficio.estadoId),
    categoriaId: Number(this.beneficio.categoriaId),
  };
  console.log(JSON.stringify(beneficioToSend, null, 2)); 
  const request$ = this.beneficio.idBeneficio > 0 
    ? this.beneficioService.editarBeneficio(beneficioToSend) 
    : this.beneficioService.guardarActividad(beneficioToSend);

  request$.subscribe({
    next: () => {
      Swal.fire("Éxito", "Operación realizada correctamente", "success");
      this.router.navigate(['/app/beneficios']);
    },
    error: (err) => {
      const mensaje = err.error?.mensaje || 'Error inesperado';
      Swal.fire("Error", mensaje, "error");
    }
  });
}


  

  cargarCategorias(): void{
    this.categoriaService.listadoCagtegorias().subscribe(
      data => {
        this.categorias = data;
        console.log("Datos recibidos de categorías:", data)
      }
    )
  }



}
