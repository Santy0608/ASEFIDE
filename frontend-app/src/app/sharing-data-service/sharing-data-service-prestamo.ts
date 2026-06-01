import { EventEmitter, Injectable } from "@angular/core";
import { Prestamo } from "../domain/Prestamo";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServicePrestamo {

  private _newPrestamoEventEmitter: EventEmitter<Prestamo> = new EventEmitter();
  
  private _idPrestamoEventEmitter = new EventEmitter();
  
  private _findPrestamoEventEmitter = new EventEmitter();

  private _selectPrestamoEventEmitter = new EventEmitter();

  private _errorsPrestamoFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsPrestamoFormEventEmitter(){
    return this._errorsPrestamoFormEventEmitter;
  }

  get newPrestamoEventEmitter(): EventEmitter<Prestamo>{
    return this._newPrestamoEventEmitter;
  }
  
  get idPrestamoEventEmitter(): EventEmitter<Number>{
    return this._idPrestamoEventEmitter;
  }

  get findPrestamoEventEmitter(){
    return this._findPrestamoEventEmitter;
  }

  get selectPrestamoEventEmitter(){
    return this._selectPrestamoEventEmitter;
  }

  private _pagePrestamoEventEmitter = new EventEmitter();

  get pagePrestamoEventEmitter() {
    return this._pagePrestamoEventEmitter;
  }

}
