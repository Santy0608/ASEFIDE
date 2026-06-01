import { EventEmitter, Injectable } from "@angular/core";
import { Usuario } from "../domain/Usuario";

@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceUsuario {

  private _newUsuarioEventEmitter: EventEmitter<Usuario> = new EventEmitter();
  
  private _idUsuarioEventEmitter = new EventEmitter();
  
  private _findUsuarioEventEmitter = new EventEmitter();

  private _selectUsuarioEventEmitter = new EventEmitter();

  private _errorsUsuarioFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsUsuariosFormEventEmitter(){
    return this._errorsUsuarioFormEventEmitter;
  }

  get newUsuarioEventEmitter(): EventEmitter<Usuario>{
    return this._newUsuarioEventEmitter;
  }
  
  get idUserEventEmitter(): EventEmitter<Number>{
    return this._idUsuarioEventEmitter;
  }

  get findUsuarioEventEmitter(){
    return this._findUsuarioEventEmitter;
  }

  get selectUsuarioEventEmitter(){
    return this._selectUsuarioEventEmitter;
  }

  private _pageUsuariosEventEmitter = new EventEmitter();

  get pageUsuariosEventEmitter() {
    return this._pageUsuariosEventEmitter;
  }

}
