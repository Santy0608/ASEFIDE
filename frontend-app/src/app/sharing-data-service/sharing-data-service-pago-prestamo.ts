import { Injectable, EventEmitter } from '@angular/core';
import { PagosPrestamos } from '../domain/PagosPrestamos';

@Injectable({
  providedIn: 'root'
})
export class SharingDataServicePago {

  private _newPagoEventEmitter: EventEmitter<PagosPrestamos> = new EventEmitter();
  
  private _idPagoEventEmitter = new EventEmitter<number>();
  
  private _findPagoEventEmitter = new EventEmitter<PagosPrestamos>();

  private _selectPagoEventEmitter = new EventEmitter<PagosPrestamos>();

  private _errorsPagoFormEventEmitter = new EventEmitter<any>();

  constructor() { }

  get errorsPagoFormEventEmitter() {
    return this._errorsPagoFormEventEmitter;
  }

  get newPagoEventEmitter(): EventEmitter<PagosPrestamos> {
    return this._newPagoEventEmitter;
  }
  
  get idPagoEventEmitter(): EventEmitter<number> {
    return this._idPagoEventEmitter;
  }

  get findPagoEventEmitter() {
    return this._findPagoEventEmitter;
  }

  get selectPagoEventEmitter() {
    return this._selectPagoEventEmitter;
  }

  private _pagePagoEventEmitter = new EventEmitter();

  get pagePagoEventEmitter() {
    return this._pagePagoEventEmitter;
  }


}