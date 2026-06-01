import { Component, OnInit } from '@angular/core';
import { Categoria } from '../../domain/Categoria';
import { CategoriaService } from '../../services/categoria.service';
import { SharingDataServiceCategoria } from '../../sharing-data-service/sharing-data-service-categoria';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categoria-form',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './categoria-form.component.html',
})
export class CategoriaFormComponent implements OnInit{

  errors: any;
  categoria!: Categoria;

  constructor(private categoriaService: CategoriaService,
              private sharingDataService: SharingDataServiceCategoria,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.categoria = new Categoria();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsCategoriaFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectCategoriaEventEmitter.subscribe(categoria => this.categoria = categoria);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idCategoria') || '0');
      if (id > 0){
        this.categoriaService.buscarCategoriaPorId(id).subscribe(categoria => this.categoria = categoria);
      }
    })
  }

  onSubmit(categoriaForm: NgForm): void {

  if (categoriaForm.invalid) return;

  console.log("Datos enviados:", this.categoria);

  if (this.categoria.idCategoria > 0) {

    this.categoriaService.editarCategoria(this.categoria).subscribe({
      next: () => {
        Swal.fire({
          title: "Actualizado",
          text: "Categoría actualizada correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/categorias']);
        });
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  } else {

    this.categoriaService.guardarCategoria(this.categoria).subscribe({
      next: () => {
        Swal.fire({
          title: "Creado",
          text: "Categoría creada correctamente",
          icon: "success"
        }).then(() => {
          this.router.navigate(['/app/categorias']);
        });
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
      }
    });

  }

}

}
