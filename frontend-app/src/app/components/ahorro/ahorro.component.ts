import { Component, OnInit } from '@angular/core';
import { AhorroService } from '../../services/ahorro.service';
import { CuentasAhorro } from '../../domain/CuentasAhorro';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { PaginatorComponent } from '../paginator/paginator.component';
import { SharingDataServiceCuentasAhorro } from '../../sharing-data-service/sharing-data-service-cuentas-ahorro';
import { switchMap } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ahorro',
  imports: [RouterModule, CommonModule, RouterLink, PaginatorComponent, FormsModule],
  templateUrl: './ahorro.component.html',
  styleUrl: './ahorro.component.css'
})
export class AhorroComponent implements OnInit{

  errors: any;
  ahorros: CuentasAhorro[] = [];
  ahorrosOriginal: CuentasAhorro[] = [];

  paginator: any = {};
  totalAhorros: number = 0;
  promedioAhorros: number = 0;

  montoFiltro: number | null = null;
  filtrando: boolean = false;

  // Top 10
  topAhorros: CuentasAhorro[] = [];
  tabActivo: 'mas' | 'menos' = 'mas';

  // Modal reporte
  modalReporteVisible: boolean = false;
  reporteAhorros: CuentasAhorro[] = [];
  cargandoReporte: boolean = false;
  usuarioSeleccionado: any = null;

  constructor(private ahorroService: AhorroService, private router: Router, private route: ActivatedRoute, private sharingDataService: SharingDataServiceCuentasAhorro){

  }

  ngOnInit(): void {
    this.listadoAhorrosCompletos();
    this.sumaTotalAhorros();
    this.promedioTotalAhorros();
    this.cargarMetricas();
  }

  cargarMetricas(): void {
    this.ahorroService.getTotalAhorros().subscribe({
      next: (data) => this.totalAhorros = data,
      error: (err) => console.error(err)
    });
    this.ahorroService.getPromedioAhorros().subscribe({
      next: (data) => this.promedioAhorros = data,
      error: (err) => console.error(err)
    });
  }

  filtrarPorMonto(): void {
    if (!this.montoFiltro) return;
    this.filtrando = true;
    this.ahorroService.usuariosMayorAhorro(this.montoFiltro).subscribe({
      next: (data) => this.ahorros = data,
      error: (err) => console.error(err)
    });
  }

  limpiarFiltro(): void{
    this.montoFiltro = null;
    this.filtrando = false;
    this.ahorros = [...this.ahorrosOriginal];
  }

  sumaTotalAhorros(): void{
    this.ahorroService.getTotalAhorros().subscribe(
      data => this.totalAhorros = data
    );
  }

  promedioTotalAhorros(): void{
    this.ahorroService.getPromedioAhorros().subscribe(
      data => this.promedioAhorros = data
    );
  }


    // =============================================
  // Top 10 con tabs — IDEA 3
  // =============================================
  cambiarTab(tab: 'mas' | 'menos'): void {
    this.tabActivo = tab;
    if (tab === 'mas') {
      this.cargarTop10Mas();
    } else {
      this.cargarTop10Menos();
    }
  }

  cargarTop10Mas(): void {
    this.ahorroService.top10MasAhorros().subscribe({
      next: (data) => this.topAhorros = data,
      error: (err) => console.error(err)
    });
  }

  cargarTop10Menos(): void {
    this.ahorroService.top10MenosAhorros().subscribe({
        next: (data) => {
            console.log(data); 
            this.topAhorros = data ?? [];
        }
    });
  }

  verReporte(ahorro: any): void {
      this.usuarioSeleccionado = ahorro;
      this.cargandoReporte = true;
      this.modalReporteVisible = true;

      this.ahorroService.reporteAhorrosUsuario(ahorro.usuarioId).subscribe({
          next: (data) => {
              this.reporteAhorros = data ?? [];  // ← si llega null, usa []
              this.cargandoReporte = false;
          },
          error: (err) => {
              console.error(err);
              this.reporteAhorros = [];          // ← también proteger el error
              this.cargandoReporte = false;
          }
      });
  }

  cerrarModalReporte(): void {
    this.modalReporteVisible = false;
    this.reporteAhorros = [];
    this.usuarioSeleccionado = null;
  }

  getInitials(nombre: string | undefined, apellido: string | undefined): string {
    const n = nombre ? nombre.charAt(0) : '';
    const a = apellido ? apellido.charAt(0) : '';
    return (n + a).toUpperCase();
  }
    
 
  OnSelectedCuentaAhorro(cuentaAhorro: CuentasAhorro): void{
    this.router.navigate(['/app/cuentas-ahorros/editar-cuenta-ahorro', cuentaAhorro.idAhorro])
  }
  
  
  eliminarCuentaAhorro(idCuentaAhorro: number): void {
  
      Swal.fire({
        title: "¿Eliminar cuenta ahorro?",
        text: "La cuenta de ahorro se marcará como inactiva.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
      }).then((result) => {
  
        if (result.isConfirmed) {
  
          this.ahorroService.eliminarCuentaAhorro(idCuentaAhorro).subscribe({
  
            next: () => {
  
              Swal.fire(
                "Eliminado",
                "La cuenta de ahorro fue eliminada correctamente",
                "success"
              );
  
             this.listadoAhorros();
  
            },
  
            error: (err) => {
              const mensaje = err.error?.mensaje || 'Error inesperado';
              Swal.fire("Error", mensaje, "error");
            }
  
          });
  
        }
  
      });
  
    }
  
    listadoAhorros(): void{
        this.route.paramMap.pipe(
        switchMap(params => {
        const page = +(params.get('page') || '0');
          return this.ahorroService.listadoAhorrosPaginacion(page);
        })
        ).subscribe(pageable => {
          this.ahorros = pageable.content as CuentasAhorro[];
          this.paginator = pageable;
        });
      }

    listadoAhorrosCompletos(): void{
      this.ahorroService.listadoCuentasAhorrosCompletos().subscribe(
        data => {
          this.ahorros = data;
        }
      )
    }
    
      pageAhorrosEvent(): void{
        this.sharingDataService.pageAhorrosEventEmitter.subscribe(pageable => {
          this.ahorros = pageable.ahorros;
          this.paginator = pageable.paginator;
        })
      }
  

}
