import { EventEmitter, Injectable } from "@angular/core";
import { TipoReporte } from "../domain/TipoReporte";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceTipoReporte {

  private _newTipoReporteEventEmitter: EventEmitter<TipoReporte> = new EventEmitter();
  
  private _idTipoReporteEventEmitter = new EventEmitter();
  
  private _findTipoReporteEventEmitter = new EventEmitter();

  private _selectTipoReporteEventEmitter = new EventEmitter();

  private _errorsTipoReporteFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsTipoReporteFormEventEmitter(){
    return this._errorsTipoReporteFormEventEmitter;
  }

  get newTipoReporteEventEmitter(): EventEmitter<TipoReporte>{
    return this._newTipoReporteEventEmitter;
  }
  
  get idTipoReporteEventEmitter(): EventEmitter<Number>{
    return this._idTipoReporteEventEmitter;
  }

  get findTipoReporteEventEmitter(){
    return this._findTipoReporteEventEmitter;
  }

  get selectTipoReporteEventEmitter(){
    return this._selectTipoReporteEventEmitter;
  }

  
  private _pageTipoReporteEventEmitter = new EventEmitter();

  get pageTipoReporteEventEmitter() {
    return this._pageTipoReporteEventEmitter;
  }

}