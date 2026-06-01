import { EventEmitter, Injectable } from "@angular/core";
import { TipoAhorro } from "../domain/TipoAhorro";




@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceTipoAhorro {

  private _newTipoAhorroEventEmitter: EventEmitter<TipoAhorro> = new EventEmitter();
  
  private _idTipoAhorroEventEmitter = new EventEmitter();
  
  private _findTipoAhorroEventEmitter = new EventEmitter();

  private _selectTipoAhorroEventEmitter = new EventEmitter();

  private _errorsTipoAhorroFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsTipoAhorroFormEventEmitter(){
    return this._errorsTipoAhorroFormEventEmitter;
  }

  get newTipoAhorroEventEmitter(): EventEmitter<TipoAhorro>{
    return this._newTipoAhorroEventEmitter;
  }
  
  get idTipoAhorroEventEmitter(): EventEmitter<Number>{
    return this._idTipoAhorroEventEmitter;
  }

  get findTipoAhorroEventEmitter(){
    return this._findTipoAhorroEventEmitter;
  }

  get selectTipoAhorroEventEmitter(){
    return this._selectTipoAhorroEventEmitter;
  }

  private _pageTiposAhorroEventEmitter = new EventEmitter();

  get pageTiposAhorroEventEmitter() {
    return this._pageTiposAhorroEventEmitter;
  }

}