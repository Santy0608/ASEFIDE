import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Reporte } from '../../domain/Reporte';
import { Usuario } from '../../domain/Usuario';
import { TipoReporte } from '../../domain/TipoReporte';
import { ModuloReporte } from '../../domain/ModuloReporte';
import { UsuarioService } from '../../services/usuario.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServiceReporte } from '../../sharing-data-service/sharing-data-service-reporte';
import { ReporteService } from '../../services/reporte.service';
import Swal from 'sweetalert2';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommonModule } from '@angular/common';
import { TipoReporteService } from '../../services/tipo-reporte.service';
import { ModuloReporteService } from '../../services/modulo-reporte.service';

@Component({
  selector: 'app-reporte-form',
  imports: [NgSelectModule, CommonModule, RouterModule, FormsModule],
  templateUrl: './reporte-form.component.html'
})
export class ReporteFormComponent implements OnInit {

  errors: any;
  reporte!: Reporte;
  
  usuarios: Usuario[] = [];
  tiposReporte: TipoReporte[] = []; 
  modulosReporte: ModuloReporte[] = [];


  constructor(
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
    private router: Router,
    private sharingDataService: SharingDataServiceReporte,
    private tipoReporteService: TipoReporteService,
    private modulosReporteService: ModuloReporteService,
    private reporteService: ReporteService,
  ) {
    this.reporte = new Reporte();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsReporteFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectReporteEventEmitter.subscribe(reporte => this.reporte = reporte);
    
    this.route.paramMap.subscribe(params => {
      const id: number = +(params.get('idReporte') || '0');
      if (id > 0) {
        this.reporteService.buscarReportePorId(id).subscribe(reporte => this.reporte = reporte);
      }
    });

    this.cargarCatalogos();
    this.cargarModulosReportes();
    this.cargarTiposReportes();
    this.cargarUsuarios();
  }

  cargarCatalogos(): void {
    this.usuarioService.listadoUsuarios().subscribe(data => this.usuarios = data);
  }

  onSubmit(reporteForm: NgForm): void {
    if (reporteForm.invalid) return;

    const reporteToSend = {
      fechaInicio: this.reporte.fechaInicio,
      fechaFinal: this.reporte.fechaFinal,
      
      tipoReporteId: Number(this.reporte.tipoReporteId),
      moduloReporteId: Number(this.reporte.moduloReporteId),
      usuarioId: Number(this.reporte.usuarioId),
      estadoId: 9
    };

    this.reporteService.guardarReporte(reporteToSend).subscribe({
      next: () => {
        Swal.fire("Éxito", "Reporte generado correctamente", "success");
        this.router.navigate(['/app/reportes']);
      },
      error: (err) => {
        const mensaje = err.error?.mensaje || 'Error inesperado';
        Swal.fire("Error", mensaje, "error");
        this.sharingDataService.errorsReporteFormEventEmitter.emit(err);
      }
    });
  }


  cargarTiposReportes(): void{
    this.tipoReporteService.listadoTipoReportes().subscribe(
      data => {
        this.tiposReporte = data;
      }
    )
  }

  cargarModulosReportes(): void{
    this.modulosReporteService.listadoModuloReportes().subscribe(
      data => {
        this.modulosReporte = data;
      }
    )
  }


  cargarUsuarios(): void{
    this.usuarioService.listarUsuariosCompletos().subscribe(
      data => {
        this.usuarios = data;
      }
    )
  }


}