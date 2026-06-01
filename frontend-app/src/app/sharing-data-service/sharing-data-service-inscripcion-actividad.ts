import { EventEmitter, Injectable } from "@angular/core";
import { InscripcionesActividad } from "../domain/inscripciones-actividad";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceInscripcionActividad {

  private _newInscripcionActividadEventEmitter: EventEmitter<InscripcionesActividad> = new EventEmitter();
  
  private _idInscripcionActividadEventEmitter = new EventEmitter();
  
  private _findInscripcionActividadEventEmitter = new EventEmitter();

  private _selectInscripcionActividadEventEmitter = new EventEmitter();

  private _errorsInscripcionActividadFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsInscripcionActividadFormEventEmitter(){
    return this._errorsInscripcionActividadFormEventEmitter;
  }

  get newInscripcionActividadEventEmitter(): EventEmitter<InscripcionesActividad>{
    return this._newInscripcionActividadEventEmitter;
  }
  
  get idInscripcionActividadEventEmitter(): EventEmitter<Number>{
    return this._idInscripcionActividadEventEmitter;
  }

  get findInscripcionActividadEventEmitter(){
    return this._findInscripcionActividadEventEmitter;
  }

  get selectInscripcionActividadEventEmitter(){
    return this._selectInscripcionActividadEventEmitter;
  }

  private _pageInscripcionActividadEventEmitter = new EventEmitter();

  get pageInscripcionActividadEventEmitter() {
    return this._pageInscripcionActividadEventEmitter;
  }

  
}
