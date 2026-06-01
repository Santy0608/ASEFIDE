import { Injectable, EventEmitter } from "@angular/core";
import { Usuario } from "../domain/Usuario";
import { Telefono } from "../domain/Telefono";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceTelefono {

  private _newTelefonoEventEmitter: EventEmitter<Telefono> = new EventEmitter();
  
  private _idTelefonoEventEmitter = new EventEmitter();
  
  private _findTelefonoEventEmitter = new EventEmitter();

  private _selectTelefonoEventEmitter = new EventEmitter();

  private _errorsTelefonoFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsTelefonosFormEventEmitter(){
    return this._errorsTelefonoFormEventEmitter;
  }

  get newTelefonoEventEmitter(): EventEmitter<Telefono>{
    return this._newTelefonoEventEmitter;
  }
  
  get idTelefonoEventEmitter(): EventEmitter<Number>{
    return this._idTelefonoEventEmitter;
  }

  get findTelefonoEventEmitter(){
    return this._findTelefonoEventEmitter;
  }

  get selectTelefonoEventEmitter(){
    return this._selectTelefonoEventEmitter;
  }

  private _pageAhorrosEventEmitter = new EventEmitter();

  get pageTelefonosEventEmitter() {
    return this._pageAhorrosEventEmitter;
  }

}
