import { EventEmitter, Injectable } from "@angular/core";
import { ModuloReporte } from "../domain/ModuloReporte";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceModuloReporte {

  private _newModuloReporteEventEmitter: EventEmitter<ModuloReporte> = new EventEmitter();
  
  private _idModuloReporteEventEmitter = new EventEmitter();
  
  private _findModuloReporteEventEmitter = new EventEmitter();

  private _selectModuloReporteEventEmitter = new EventEmitter();

  private _errorsModuloReporteFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsModuloReporteFormEventEmitter(){
    return this._errorsModuloReporteFormEventEmitter;
  }

  get newModuloReporteEventEmitter(): EventEmitter<ModuloReporte>{
    return this._newModuloReporteEventEmitter;
  }
  
  get idModuloReporteEventEmitter(): EventEmitter<Number>{
    return this._idModuloReporteEventEmitter;
  }

  get findModuloReporteEventEmitter(){
    return this._findModuloReporteEventEmitter;
  }

  get selectModuloReporteEventEmitter(){
    return this._selectModuloReporteEventEmitter;
  }

  private _pageModuloReporteEventEmitter = new EventEmitter();

  get pageModuloReporteEventEmitter() {
    return this._pageModuloReporteEventEmitter;
  }

}