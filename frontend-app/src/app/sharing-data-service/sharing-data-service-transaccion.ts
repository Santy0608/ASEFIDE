import { EventEmitter, Injectable } from "@angular/core";
import { Transaccion } from "../domain/Transaccion";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceTransaccion {

  private _newTransaccionEventEmitter: EventEmitter<Transaccion> = new EventEmitter();
  
  private _idTransaccionEventEmitter = new EventEmitter();
  
  private _findTransaccionEventEmitter = new EventEmitter();

  private _selectTransaccionEventEmitter = new EventEmitter();

  private _errorsTransaccionFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsTransaccionFormEventEmitter(){
    return this._errorsTransaccionFormEventEmitter;
  }

  get newTransaccionEventEmitter(): EventEmitter<Transaccion>{
    return this._newTransaccionEventEmitter;
  }
  
  get idCategoriaEventEmitter(): EventEmitter<Number>{
    return this._idTransaccionEventEmitter;
  }

  get findTransaccionEventEmitter(){
    return this._findTransaccionEventEmitter;
  }

  get selectTransaccionEventEmitter(){
    return this._selectTransaccionEventEmitter;
  }

  private _pageUsuariosEventEmitter = new EventEmitter();

  get pageUsuariosEventEmitter() {
    return this._pageUsuariosEventEmitter;
  }

}
