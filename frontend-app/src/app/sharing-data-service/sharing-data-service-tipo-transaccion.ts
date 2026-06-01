import { EventEmitter, Injectable } from "@angular/core";
import { TipoTransaccion } from "../domain/TipoTransaccion";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceTipoTransaccion {

  private _newTipoTransaccionEventEmitter: EventEmitter<TipoTransaccion> = new EventEmitter();
  
  private _idTipoTransaccionEventEmitter = new EventEmitter();
  
  private _findTipoTransaccionEventEmitter = new EventEmitter();

  private _selectTipoTransaccionEventEmitter = new EventEmitter();

  private _errorsTipoTransaccionFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsTipoTransaccionFormEventEmitter(){
    return this._errorsTipoTransaccionFormEventEmitter;
  }

  get newTipoAhorroEventEmitter(): EventEmitter<TipoTransaccion>{
    return this._newTipoTransaccionEventEmitter;
  }
  
  get idTipoTransaccionEventEmitter(): EventEmitter<Number>{
    return this._idTipoTransaccionEventEmitter;
  }

  get findTipoTransaccionEventEmitter(){
    return this._findTipoTransaccionEventEmitter;
  }

  get selectTipoTransaccionEventEmitter(){
    return this._selectTipoTransaccionEventEmitter;
  }
    
  private _pageTipoTransaccionEventEmitter = new EventEmitter();

  get pageTipoTransaccionEventEmitter() {
    return this._pageTipoTransaccionEventEmitter;
  }


}