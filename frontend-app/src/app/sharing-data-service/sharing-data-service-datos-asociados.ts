import { EventEmitter, Injectable } from "@angular/core";
import { Categoria } from "../domain/Categoria";
import { DatosAsociados } from "../domain/DatosAsociados";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceDatosAsociados {

  private _newDatosAsociadosEventEmitter: EventEmitter<DatosAsociados> = new EventEmitter();
  
  private _idDatosAsociadosEventEmitter = new EventEmitter();
  
  private _findDatosAsociadosEventEmitter = new EventEmitter();

  private _selectDatosAsociadosEventEmitter = new EventEmitter();

  private _errorsDatosAsociadosFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsDatosAsociadosFormEventEmitter(){
    return this._errorsDatosAsociadosFormEventEmitter;
  }

  get newDatosAsociadosEventEmitter(): EventEmitter<DatosAsociados>{
    return this._newDatosAsociadosEventEmitter;
  }
  
  get idDatosAsociadosEventEmitter(): EventEmitter<Number>{
    return this._idDatosAsociadosEventEmitter;
  }

  get findDatosAsociadosEventEmitter(){
    return this._findDatosAsociadosEventEmitter;
  }

  get selectDatosAsociadosEventEmitter(){
    return this._selectDatosAsociadosEventEmitter;
  }

  private _pageDatosAsociadosEventEmitter = new EventEmitter();

  get pageDatosAsociadosEventEmitter() {
    return this._pageDatosAsociadosEventEmitter;
  }

  


}
