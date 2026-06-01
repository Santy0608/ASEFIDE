import { EventEmitter, Injectable } from "@angular/core";
import { Estado } from "../domain/Estado";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceEstado {

  private _newEstadoEventEmitter: EventEmitter<Estado> = new EventEmitter();
  
  private _idEstadoEventEmitter = new EventEmitter();
  
  private _findEstadoEventEmitter = new EventEmitter();

  private _selectEstadoEventEmitter = new EventEmitter();

  private _errorsEstadoFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsEstadoFormEventEmitter(){
    return this._errorsEstadoFormEventEmitter;
  }

  get newEstadoEventEmitter(): EventEmitter<Estado>{
    return this._newEstadoEventEmitter;
  }
  
  get idEstadoEventEmitter(): EventEmitter<Number>{
    return this._idEstadoEventEmitter;
  }

  get findEstadoEventEmitter(){
    return this._findEstadoEventEmitter;
  }

  get selectEstadoEventEmitter(){
    return this._selectEstadoEventEmitter;
  }


}
