import { EventEmitter, Injectable } from "@angular/core";
import { Reporte } from "../domain/Reporte";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceReporte {

  private _newReporteEventEmitter: EventEmitter<Reporte> = new EventEmitter();
  
  private _idReporteEventEmitter = new EventEmitter<number>();
  
  private _findReporteEventEmitter = new EventEmitter<Reporte>();

  private _selectReporteEventEmitter = new EventEmitter<Reporte>();

  private _errorsReporteFormEventEmitter = new EventEmitter<any>();

  constructor() { }

  get errorsReporteFormEventEmitter() {
    return this._errorsReporteFormEventEmitter;
  }

  get newReporteEventEmitter(): EventEmitter<Reporte> {
    return this._newReporteEventEmitter;
  }
  
  get idReporteEventEmitter(): EventEmitter<number> {
    return this._idReporteEventEmitter;
  }

  get findReporteEventEmitter() {
    return this._findReporteEventEmitter;
  }

  get selectReporteEventEmitter() {
    return this._selectReporteEventEmitter;
  }

  private _pageReporteEventEmitter = new EventEmitter();

  get pageReporteEventEmitter() {
    return this._pageReporteEventEmitter;
  }
}