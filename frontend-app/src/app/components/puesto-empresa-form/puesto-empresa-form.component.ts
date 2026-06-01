import { Component, OnInit } from '@angular/core';
import { PuestoEmpresa } from '../../domain/PuestoEmpresa';
import { PuestoEmpresaService } from '../../services/puesto-empresa.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SharingDataServicePuestoEmpresa } from '../../sharing-data-service/sharing-data-service-puesto-empresa';
import Swal from 'sweetalert2';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-puesto-empresa-form',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './puesto-empresa-form.component.html',
})
export class PuestoEmpresaFormComponent implements OnInit{


 errors: any;
  puestoEmpresa!: PuestoEmpresa;
  puestosEmpresas: PuestoEmpresa[] = [];

  constructor(private puestoEmpresaService: PuestoEmpresaService,
              private sharingDataService: SharingDataServicePuestoEmpresa,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.puestoEmpresa = new PuestoEmpresa();
  }

  ngOnInit(): void {
    this.sharingDataService.errorsPuestoEmpresaFormEventEmitter.subscribe(errors => this.errors = errors);
    this.sharingDataService.selectPuestoEmpresaEventEmitter.subscribe(puestoEmpresa => this.puestoEmpresa = puestoEmpresa);
    this.route.paramMap.subscribe(params => {
      const id:number = +(params.get('idPuestoEmpresa') || '0');
      if (id > 0){
        this.puestoEmpresaService.buscarPuestoEmpresaPorId(id).subscribe(puestoEmpresa => this.puestoEmpresa = puestoEmpresa);
      }
    })
  }

  

  onSubmit(puestoEmpresaForm: NgForm): void {

      if (puestoEmpresaForm.invalid) return;

      console.log("Datos enviados:", this.puestoEmpresa);
      if (this.puestoEmpresa.idPuestoEmpresa > 0) {

        this.puestoEmpresaService.actualizarPuestoEmpresa(this.puestoEmpresa).subscribe({
          next: () => {
            Swal.fire({
              title: "Actualizado",
              text: "Puesto Empresa actualizado correctamente",
              icon: "success"
            }).then(() => {
              this.router.navigate(['/app/puestos-empresas']);
            });
          },
          error: (err) => {
            console.error(err);
          }
        });

      } else {

        this.puestoEmpresaService.guardarPuestoEmpresa(this.puestoEmpresa).subscribe({
          next: () => {
            Swal.fire({
              title: "Creado",
              text: "Puesto Empresa creado correctamente",
              icon: "success"
            }).then(() => {
              this.router.navigate(['/app/puestos-empresas']);
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
