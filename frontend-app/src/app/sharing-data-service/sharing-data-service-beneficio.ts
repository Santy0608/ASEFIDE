import { EventEmitter, Injectable } from "@angular/core";
import { Beneficio } from "../domain/Beneficio";


@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceBeneficio {

  private _newBeneficioEventEmitter: EventEmitter<Beneficio> = new EventEmitter();
  
  private _idBeneficioEventEmitter = new EventEmitter();
  
  private _findBeneficioEventEmitter = new EventEmitter();

  private _selectBeneficioEventEmitter = new EventEmitter();

  private _errorsBeneficioFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsBeneficioFormEventEmitter(){
    return this._errorsBeneficioFormEventEmitter;
  }

  get newBeneficioEventEmitter(): EventEmitter<Beneficio>{
    return this._newBeneficioEventEmitter;
  }
  
  get idBeneficioEventEmitter(): EventEmitter<Number>{
    return this._idBeneficioEventEmitter;
  }

  get findBeneficioEventEmitter(){
    return this._findBeneficioEventEmitter;
  }

  get selectBeneficioEventEmitter(){
    return this._selectBeneficioEventEmitter;
  }

  private _pageBeneficiosEventEmitter = new EventEmitter();
  
  get pageBeneficiosEventEmitter() {
    return this._pageBeneficiosEventEmitter;
  }

}
