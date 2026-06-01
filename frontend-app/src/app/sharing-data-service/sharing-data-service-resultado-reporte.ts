import { EventEmitter, Injectable } from "@angular/core";
import { ResultadoReporte } from "../domain/ResultadosReporte";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceResultadoReporte {

  private _newResultadoReporteEventEmitter: EventEmitter<ResultadoReporte> = new EventEmitter();
  
  private _idResultadoReporteEventEmitter = new EventEmitter();
  
  private _findResultadoReporteEventEmitter = new EventEmitter();

  private _selectResultadoReporteEventEmitter = new EventEmitter();

  private _errorsResultadoReporteFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsResultadoReporteFormEventEmitter(){
    return this._errorsResultadoReporteFormEventEmitter;
  }

  get newActividadEventEmitter(): EventEmitter<ResultadoReporte>{
    return this._newResultadoReporteEventEmitter;
  }
  
  get idResultadoReporteEventEmitter(): EventEmitter<Number>{
    return this._idResultadoReporteEventEmitter;
  }

  get findResultadoReporteEventEmitter(){
    return this._findResultadoReporteEventEmitter;
  }

  get selectResultadoReporteEventEmitter(){
    return this._selectResultadoReporteEventEmitter;
  }

  private _pageResultadosReporteEventEmitter = new EventEmitter();
  
  get pageResultadosReporteEventEmitter() {
    return this._pageResultadosReporteEventEmitter;
  }

}