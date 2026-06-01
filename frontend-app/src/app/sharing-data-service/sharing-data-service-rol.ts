import { EventEmitter, Injectable } from "@angular/core";
import { Rol } from "../domain/Rol";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceRol {

  private _newRolEventEmitter: EventEmitter<Rol> = new EventEmitter();
  
  private _idRolEventEmitter = new EventEmitter();
  
  private _findRolEventEmitter = new EventEmitter();

  private _selectRolEventEmitter = new EventEmitter();

  private _errorsRolFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsRolFormEventEmitter(){
    return this._errorsRolFormEventEmitter;
  }

  get newRolEventEmitter(): EventEmitter<Rol>{
    return this._newRolEventEmitter;
  }
  
  get idRolEventEmitter(): EventEmitter<Number>{
    return this._idRolEventEmitter;
  }

  get findRolEventEmitter(){
    return this._findRolEventEmitter;
  }

  get selectRolEventEmitter(){
    return this._selectRolEventEmitter;
  }

  private _pageRolEventEmitter = new EventEmitter();
  
  get pageRolEventEmitter() {
    return this._pageRolEventEmitter;
  }


}
