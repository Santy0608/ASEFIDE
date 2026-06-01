import { EventEmitter, Injectable } from "@angular/core";
import { Categoria } from "../domain/Categoria";



@Injectable({
  providedIn: 'root'
})
export class SharingDataServiceCategoria {

  private _newCategoriaEventEmitter: EventEmitter<Categoria> = new EventEmitter();
  
  private _idCategoriaEventEmitter = new EventEmitter();
  
  private _findCategoriaEventEmitter = new EventEmitter();

  private _selectCategoriaEventEmitter = new EventEmitter();

  private _errorsCategoriaFormEventEmitter = new EventEmitter();

  constructor() {

  }

  get errorsCategoriaFormEventEmitter(){
    return this._errorsCategoriaFormEventEmitter;
  }

  get newCategoriaEventEmitter(): EventEmitter<Categoria>{
    return this._newCategoriaEventEmitter;
  }
  
  get idCategoriaEventEmitter(): EventEmitter<Number>{
    return this._idCategoriaEventEmitter;
  }

  get findCategoriaEventEmitter(){
    return this._findCategoriaEventEmitter;
  }

  get selectCategoriaEventEmitter(){
    return this._selectCategoriaEventEmitter;
  }

  private _pageCategoriasEventEmitter = new EventEmitter();
  
  get pageCategoriasEventEmitter() {
    return this._pageCategoriasEventEmitter;
  }

}
