import { EventEmitter, Injectable } from "@angular/core";
import { Categoria } from "../domain/Categoria";
import { PuestoEmpresa } from "../domain/PuestoEmpresa";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServicePuestoEmpresa {

  private _newPuestoEmpresaEventEmitter: EventEmitter<PuestoEmpresa> = new EventEmitter();
  
  private _idPuestoEmpresaEventEmitter = new EventEmitter();
  
  private _findPuestoEmpresaEventEmitter = new EventEmitter();

  private _selectPuestoEmpresaEventEmitter = new EventEmitter();

  private _errorsPuestoEmpresaFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsPuestoEmpresaFormEventEmitter(){
    return this._errorsPuestoEmpresaFormEventEmitter;
  }

  get newPuestoEmpresaEventEmitter(): EventEmitter<PuestoEmpresa>{
    return this._newPuestoEmpresaEventEmitter;
  }
  
  get idPuestoEmpresaEventEmitter(): EventEmitter<Number>{
    return this._idPuestoEmpresaEventEmitter;
  }

  get findPuestoEmpresaEventEmitter(){
    return this._findPuestoEmpresaEventEmitter;
  }

  get selectPuestoEmpresaEventEmitter(){
    return this._selectPuestoEmpresaEventEmitter;
  }

  private _pagePuestoEmpresaEventEmitter = new EventEmitter();

  get pagePuestoEmpresaEventEmitter() {
    return this._pagePuestoEmpresaEventEmitter;
  }

}
