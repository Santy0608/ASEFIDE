import { Component, OnInit } from '@angular/core';
import { DatosAsociados } from '../../domain/DatosAsociados';
import { PuestoEmpresa } from '../../domain/PuestoEmpresa';
import { DatosAsociadosService } from '../../services/datos-asociados.service';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { Usuario } from '../../domain/Usuario';
import { CorreoService } from '../../services/correo.service';
import { DireccionService } from '../../services/direccion.service';
import { TelefonoService } from '../../services/telefono.service';
import { SharingDataServiceUsuario } from '../../sharing-data-service/sharing-data-service-usuario';
import { SharingDataServiceDatosAsociados } from '../../sharing-data-service/sharing-data-service-datos-asociados';
import { PuestoEmpresaService } from '../../services/puesto-empresa.service';
import { NgSelectModule } from '@ng-select/ng-select';
import { CommonModule } from '@angular/common';
import { PaginatorComponent } from '../paginator/paginator.component';

@Component({
  selector: 'app-datos-asociados-form',
  imports: [FormsModule, NgSelectModule, CommonModule, RouterModule, RouterLink],
  templateUrl: './datos-asociados-form.component.html',
})
export class DatosAsociadosFormComponent implements OnInit{


  errors: any;
  datoAsociados!: DatosAsociados;
  puestosEmpresas: PuestoEmpresa[] = [];
  puestoEmpresa!: PuestoEmpresa;

  constructor(private datosAsociadosService: DatosAsociadosService,
              private route: ActivatedRoute,
              private router: Router,
              private sharingDataService: SharingDataServiceDatosAsociados,
              private puestoEmpresaService: PuestoEmpresaService,
  ){
    this.datoAsociados = new DatosAsociados();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsDatosAsociadosFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectDatosAsociadosEventEmitter.subscribe(datosAsociados => this.datosAsociadosService = datosAsociados);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idDatosAsociados') || '0');
      if (id > 0){
        this.datosAsociadosService.buscarDatosAsociadosPorId(id).subscribe(datosAsociados => this.datoAsociados = datosAsociados);
      }
    })

    this.cargarPuestosEmpresas();
  }

  
  onSubmit(datosAsociadosForm: NgForm): void {
      if (datosAsociadosForm.invalid) return;

      const datosAsociadosToSend = {
        idDatosAsociados: this.datoAsociados.idDatosAsociados,
        fechaAfiliacion: this.datoAsociados.fechaAfiliacion,
        aportes: this.datoAsociados.aportes?.map(a => ({
            idAporte: a.idAporte,
            monto: a.monto,
            fechaInicio: a.fechaInicio,
            fechaFin: a.fechaFinal
        })),
        puestoEmpresaId: Number(this.datoAsociados.puestoEmpresaId),
    };
      console.log(JSON.stringify(datosAsociadosToSend, null, 2)); // Esto te lo imprime bonito en consola
      // Ahora decides si es guardar o editar
      const request$ = this.datoAsociados.idDatosAsociados > 0 
        ? this.datosAsociadosService.actualizarDatosAsociados(datosAsociadosToSend) 
        : this.datosAsociadosService.guardarDatosAsociados(datosAsociadosToSend);

      request$.subscribe({
        next: () => {
          Swal.fire("Éxito", "Operación realizada correctamente", "success");
          this.router.navigate(['/app/usuarios']);
        },
        error: (err) => {
          const mensaje = err.error?.mensaje || 'Error inesperado';
          Swal.fire("Error", mensaje, "error");
          this.sharingDataService.errorsDatosAsociadosFormEventEmitter.emit(err);
        }
      });
  }

cargarPuestosEmpresas(): void{
    this.puestoEmpresaService.listadoPuestoEmpresas().subscribe(data => {
    this.puestosEmpresas = data;
  })
}

  agregarAporte(): void {
    if (!this.datoAsociados.aportes) {
        this.datoAsociados.aportes = [];
    }
    this.datoAsociados.aportes.push({
        idAporte: 0,
        monto: 0,
        fechaInicio: new Date(),
        fechaFinal: undefined
    });
  }

  eliminarAporte(index: number): void {
      this.datoAsociados.aportes?.splice(index, 1);
  }

}
