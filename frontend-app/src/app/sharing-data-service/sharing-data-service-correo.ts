import { EventEmitter, Injectable } from "@angular/core";
import { Categoria } from "../domain/Categoria";
import { Correo } from "../domain/Correo";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceCorreo {

  private _newCorreoFormEventEmitter: EventEmitter<Correo> = new EventEmitter();
  
  private _idCorreoEventEmitter = new EventEmitter();
  
  private _findCorreoEventEmitter = new EventEmitter();

  private _selectCorreoEventEmitter = new EventEmitter();

  private _errorsCorreoFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsCorreoFormEventEmitter(){
    return this._errorsCorreoFormEventEmitter;
  }

  get newCorreoFormEventEmitter(): EventEmitter<Correo>{
    return this._newCorreoFormEventEmitter;
  }
  
  get idCorreoEventEmitter(): EventEmitter<Number>{
    return this._idCorreoEventEmitter;
  }

  get findCorreoEventEmitter(){
    return this._findCorreoEventEmitter;
  }

  get selectCorreoEventEmitter(){
    return this._selectCorreoEventEmitter;
  }

  private _pageCorreosEventEmitter = new EventEmitter();

  get pageCorreosEventEmitter() {
    return this._pageCorreosEventEmitter;
  }


}
