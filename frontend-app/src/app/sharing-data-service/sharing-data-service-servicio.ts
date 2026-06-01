import { EventEmitter, Injectable } from "@angular/core";
import { Servicio } from "../domain/servicio";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceServicio {

  private _newServicioEventEmitter: EventEmitter<Servicio> = new EventEmitter();
  
  private _idServicioEventEmitter = new EventEmitter();
  
  private _findServicioEventEmitter = new EventEmitter();

  private _selectServicioEventEmitter = new EventEmitter();

  private _errorsServicioFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsServicioFormEventEmitter(){
    return this._errorsServicioFormEventEmitter;
  }

  get newServicioEventEmitter(): EventEmitter<Servicio>{
    return this._newServicioEventEmitter;
  }
  
  get idServicioEventEmitter(): EventEmitter<Number>{
    return this._idServicioEventEmitter;
  }

  get findServicioEventEmitter(){
    return this._findServicioEventEmitter;
  }

  get selectServicioEventEmitter(){
    return this._selectServicioEventEmitter;
  }

  private _pageServiciosEventEmitter = new EventEmitter();

  get pageServiciosEventEmitter() {
    return this._pageServiciosEventEmitter;
  }

}
