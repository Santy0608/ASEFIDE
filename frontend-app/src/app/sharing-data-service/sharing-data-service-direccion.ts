import { EventEmitter, Injectable } from "@angular/core";
import { Direccion } from "../domain/Direccion";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceDireccion {

  private _newDireccionEventEmitter: EventEmitter<Direccion> = new EventEmitter();

  private _idDireccionEventEmitter = new EventEmitter();
  
  private _findDireccionEventEmitter = new EventEmitter();

  private _selectDireccionEventEmitter = new EventEmitter();

  private _errorsDireccionFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsDireccionFormEventEmitter(){
    return this._errorsDireccionFormEventEmitter;
  }

  get newDireccionEventEmitter(): EventEmitter<Direccion>{
    return this._newDireccionEventEmitter;
  }
  
  get idDireccionEventEmitter(): EventEmitter<Number>{
    return this._idDireccionEventEmitter;
  }

  get findDireccionEventEmitter(){
    return this._findDireccionEventEmitter;
  }

  get selectDireccionEventEmitter(){
    return this._selectDireccionEventEmitter;
  }

  private _pageDireccionEventEmitter = new EventEmitter();

  get pageDireccionEventEmitter() {
    return this._pageDireccionEventEmitter;
  }

  

}
