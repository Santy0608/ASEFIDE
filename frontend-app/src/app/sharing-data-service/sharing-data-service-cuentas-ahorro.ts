import { EventEmitter, Injectable } from "@angular/core";
import { CuentasAhorro } from "../domain/CuentasAhorro";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceCuentasAhorro {

  private _newCuentasAhorroEventEmitter: EventEmitter<CuentasAhorro> = new EventEmitter();
  
  private _idCuentasAhorroEventEmitter = new EventEmitter();
  
  private _findCuentasAhorroEventEmitter = new EventEmitter();

  private _selectCuentasAhorroEventEmitter = new EventEmitter();

  private _errorsCuentasAhorroFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsCuentasAhorroFormEventEmitter(){
    return this._errorsCuentasAhorroFormEventEmitter;
  }

  get newCuentasAhorroEventEmitter(): EventEmitter<CuentasAhorro>{
    return this._newCuentasAhorroEventEmitter;
  }
  
  get idCuentasAhorroEventEmitter(): EventEmitter<Number>{
    return this._idCuentasAhorroEventEmitter;
  }

  get findCuentasAhorroEventEmitter(){
    return this._findCuentasAhorroEventEmitter;
  }

  get selectCuentasAhorroEventEmitter(){
    return this._selectCuentasAhorroEventEmitter;
  }

  private _pageAhorrosEventEmitter = new EventEmitter();

  get pageAhorrosEventEmitter() {
    return this._pageAhorrosEventEmitter;
  }

}
