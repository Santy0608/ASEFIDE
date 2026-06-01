import { Injectable, EventEmitter } from '@angular/core';
import { MovimientosAhorro } from '../domain/MovimientosAhorro';

@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceMovimiento {

  private _newMovimientoEventEmitter: EventEmitter<MovimientosAhorro> = new EventEmitter();
  
  private _idMovimientoEventEmitter = new EventEmitter<number>();
  
  private _findMovimientoEventEmitter = new EventEmitter<MovimientosAhorro>();

  private _selectMovimientoEventEmitter = new EventEmitter<MovimientosAhorro>();

  private _errorsMovimientoFormEventEmitter = new EventEmitter<any>();

  constructor() { }

  get errorsMovimientoFormEventEmitter() {
    return this._errorsMovimientoFormEventEmitter;
  }

  get newMovimientoEventEmitter(): EventEmitter<MovimientosAhorro> {
    return this._newMovimientoEventEmitter;
  }
  
  get idMovimientoEventEmitter(): EventEmitter<number> {
    return this._idMovimientoEventEmitter;
  }

  get findMovimientoEventEmitter() {
    return this._findMovimientoEventEmitter;
  }

  get selectMovimientoEventEmitter() {
    return this._selectMovimientoEventEmitter;
  }

  private _pageMovimientosAhorroEventEmitter = new EventEmitter();

  get pageMovimientosAhorroEventEmitter() {
    return this._pageMovimientosAhorroEventEmitter;
  }
  

}