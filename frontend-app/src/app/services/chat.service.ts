import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";


export interface MensajeHistorial {
  rol: 'user' | 'assistant';
  content: string;
}

export interface ChatRequest {
  mensaje: string;
  historial: MensajeHistorial[];
}

export interface ChatResponse {
  mensaje: string;
  mensajeExitoso: boolean;
  error: string | null;
}



@Injectable({
    providedIn: 'root'
})
export class ChatService{

    private url: string = 'http://localhost:8080/api/chat';

    constructor(private http: HttpClient){

    }

    sendMessage(mensaje: string, historial: MensajeHistorial[]): Observable<ChatResponse> {
        const body: ChatRequest = { mensaje, historial };
        return this.http.post<ChatResponse>(this.url, body);
    }

}