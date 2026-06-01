import { EventEmitter, Injectable } from "@angular/core";
import { Categoria } from "../domain/Categoria";
import { LugarEvento } from "../domain/LugarEvento";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceLugarEvento {

  private _newLugarEventoEventEmitter: EventEmitter<LugarEvento> = new EventEmitter();
  
  private _idLugarEventoEventEmitter = new EventEmitter();
  
  private _findLugarEventoEventEmitter = new EventEmitter();

  private _selectLugarEventoEventEmitter = new EventEmitter();

  private _errorsLugarEventoFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsLugarEventoFormEventEmitter(){
    return this._errorsLugarEventoFormEventEmitter;
  }

  get newLugarEventoEventEmitter(): EventEmitter<LugarEvento>{
    return this._newLugarEventoEventEmitter;
  }
  
  get idLugarEventoEventEmitter(): EventEmitter<Number>{
    return this._idLugarEventoEventEmitter;
  }

  get findLugarEventoEventEmitter(){
    return this._findLugarEventoEventEmitter;
  }

  get selectLugarEventoEventEmitter(){
    return this._selectLugarEventoEventEmitter;
  }

  private _pageLugarEventoEventEmitter = new EventEmitter();

  get pageLugarEventoEventEmitter() {
    return this._pageLugarEventoEventEmitter;
  }


}
