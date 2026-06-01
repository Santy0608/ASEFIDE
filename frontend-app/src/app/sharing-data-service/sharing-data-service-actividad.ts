import { EventEmitter, Injectable } from "@angular/core";
import { Actividad } from "../domain/Actividad";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceActividad {

  private _newActividadEventEmitter: EventEmitter<Actividad> = new EventEmitter();
  
  private _idActividadEventEmitter = new EventEmitter();
  
  private _findActividadEventEmitter = new EventEmitter();

  private _selectActividadEventEmitter = new EventEmitter();

  private _errorsActividadFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsActividadFormEventEmitter(){
    return this._errorsActividadFormEventEmitter;
  }

  get newActividadEventEmitter(): EventEmitter<Actividad>{
    return this._newActividadEventEmitter;
  }
  
  get idActividadEventEmitter(): EventEmitter<Number>{
    return this._idActividadEventEmitter;
  }

  get findActividadEventEmitter(){
    return this._findActividadEventEmitter;
  }

  get selectActividadEventEmitter(){
    return this._selectActividadEventEmitter;
  }

  private _pageActividadesEventEmitter = new EventEmitter();
  
  get pageActividadesEventEmitter() {
    return this._pageActividadesEventEmitter;
  }
  
  

}