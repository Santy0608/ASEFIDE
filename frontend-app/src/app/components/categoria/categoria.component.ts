import { Component, OnInit } from '@angular/core';
import { Categoria } from '../../domain/Categoria';
import { CategoriaService } from '../../services/categoria.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceCategoria } from '../../sharing-data-service/sharing-data-service-categoria';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-categoria',
  imports: [CommonModule, RouterModule, RouterLink, PaginatorComponent, FormsModule],
  templateUrl: './categoria.component.html',
})
export class CategoriaComponent implements OnInit{

  categorias: Categoria[] = [];
  errors: any;
  paginator: any = {};
  nombreCategoriaBuscar: string = '';
  estadoBuscando: boolean = false;


  constructor(private categoriaService: CategoriaService,
              private router: Router,
              private sharingDataService: SharingDataServiceCategoria,
              private route: ActivatedRoute
  ){

  }

  ngOnInit(): void {
    this.listadoCategoriasCompletas();
  }

  listadoCategorias(): void{
    this.route.paramMap.pipe(
      switchMap(params => {
        const page = +(params.get('page') || '0');
        return this.categoriaService.listadoCategoriaPaginacion(page);
      })
      ).subscribe(pageable => {
        this.categorias = pageable.content as Categoria[];
        this.paginator = pageable;
      });
  }

  listadoCategoriasCompletas(): void{
    this.categoriaService.listadoCategoriasCompletas().subscribe(
      data => {
        this.categorias = data;
      }
    )
  }
        
    pageCategoriasEvent(): void{
      this.sharingDataService.pageCategoriasEventEmitter.subscribe(pageable => {
        this.categorias = pageable.categorias;
        this.paginator = pageable.paginator;
      })
    }

  OnSelectedCategoria(categoria: Categoria): void{
    this.router.navigate(['/app/categorias/editar-categoria', categoria.idCategoria])
  }

  buscarCategoria(): void{
    if (this.nombreCategoriaBuscar.trim() === ''){
      this.listadoCategorias();
      this.estadoBuscando = false;
      return;
    }
      
    this.categoriaService.buscarCategoriaPorNombre(this.nombreCategoriaBuscar).subscribe({
      next: (resultado) => {
        this.categorias = resultado;
        this.estadoBuscando = true;
      }, error: (err) => {
        console.log('Error', err);
      }
    })
  }

  limpiarBusqueda(): void {
    this.nombreCategoriaBuscar = '';
    this.listadoCategorias();
  }



  eliminarCategoria(idCategoria: number): void {

    Swal.fire({
      title: "¿Eliminar categoría?",
      text: "La categoría se marcará como inactiva.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Sí, eliminar",
      cancelButtonText: "Cancelar"
    }).then((result) => {

      if (result.isConfirmed) {

        this.categoriaService.eliminarCategoria(idCategoria).subscribe({

          next: () => {

            Swal.fire(
              "Eliminado",
              "La categoría fue eliminada correctamente",
              "success"
            );

            // quitar del listado sin recargar
           this.listadoCategorias();

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
