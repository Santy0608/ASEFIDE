import { Component, OnInit } from '@angular/core';
import { Servicio } from '../../domain/servicio';
import { Categoria } from '../../domain/Categoria';
import { ServicioService } from '../../services/servicio.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServiceServicio } from '../../sharing-data-service/sharing-data-service-servicio';
import { CategoriaService } from '../../services/categoria.service';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-servicio-form',
  imports: [NgSelectModule, CommonModule, RouterModule, FormsModule],
  templateUrl: './servicio-form.component.html',
})
export class ServicioFormComponent implements OnInit{

  errors: any;
  servicio!: Servicio;
  categorias: Categoria[] = [];


  constructor(private servicioService: ServicioService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServiceServicio,
              private categoriaService: CategoriaService
  ){
    this.servicio = new Servicio();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsServicioFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectServicioEventEmitter.subscribe(servicio => this.servicio = servicio);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idServicio') || '0');
      if (id > 0){
        this.servicioService.buscarServicioPorId(id).subscribe(servicio => this.servicio = servicio);
      }
    })

    this.cargarCategorias();
  }

  

  
  onSubmit(servicioForm: NgForm): void {
  if (servicioForm.invalid) return;

  // Aplanamos el objeto para el backend
  const servicioToSend = {
    idServicio: this.servicio.idServicio,
    nombreServicio: this.servicio.nombreServicio,
    descripcion: this.servicio.descripcion,
    valorEstimado: this.servicio.valorEstimado,
    stock: this.servicio.stock,
  
    // Mapeo exacto al DTO de Java
    estadoId: Number(this.servicio.estadoId),
    categoriaId: Number(this.servicio.categoriaId),
  };
  console.log(JSON.stringify(servicioToSend, null, 2)); 
  const request$ = this.servicio.idServicio > 0 
    ? this.servicioService.editarServicio(servicioToSend) 
    : this.servicioService.guardarServicio(servicioToSend);

  request$.subscribe({
    next: () => {
      Swal.fire("Éxito", "Operación realizada correctamente", "success");
      this.router.navigate(['/app/servicios']);
    },
    error: (err) => {
      const mensaje = err.error?.mensaje || 'Error inesperado';
      Swal.fire("Error", mensaje, "error");
      this.sharingDataService.errorsServicioFormEventEmitter.emit(err);
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
