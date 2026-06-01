import { Injectable, EventEmitter } from '@angular/core';
import { DetalleTransaccion } from '../domain/DetallesTransaccion';

@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceDetalle {

  private _newDetalleEventEmitter: EventEmitter<DetalleTransaccion> = new EventEmitter();
  
  private _idDetalleEventEmitter = new EventEmitter<number>();
  
  private _findDetalleEventEmitter = new EventEmitter<DetalleTransaccion>();

  private _selectDetalleEventEmitter = new EventEmitter<DetalleTransaccion>();

  private _errorsDetalleFormEventEmitter = new EventEmitter<any>();

  constructor() { }

  get errorsDetalleFormEventEmitter() {
    return this._errorsDetalleFormEventEmitter;
  }

  get newDetalleEventEmitter(): EventEmitter<DetalleTransaccion> {
    return this._newDetalleEventEmitter;
  }
  
  get idDetalleEventEmitter(): EventEmitter<number> {
    return this._idDetalleEventEmitter;
  }

  get findDetalleEventEmitter() {
    return this._findDetalleEventEmitter;
  }

  get selectDetalleEventEmitter() {
    return this._selectDetalleEventEmitter;
  }

  private _pageDetallesTransaccionesEventEmitter = new EventEmitter();

  get pageDetallesTransaccionesEventEmitter() {
    return this._pageDetallesTransaccionesEventEmitter;
  }

}