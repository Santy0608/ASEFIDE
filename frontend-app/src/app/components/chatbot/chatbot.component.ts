import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ChatService, MensajeHistorial } from '../../services/chat.service';
import { AuthService } from '../../services/auth.service';
import { TimeScale } from 'chart.js';
import { timestamp } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ChatFormatPipe } from '../../domain/ChatFormat';

export interface ChatMensaje{

  rol: 'user' | 'assistant' | 'error';
  contenido: string;
  timestamp: Date;
  loading?: boolean;

}

@Component({
  selector: 'app-chatbot',
  imports: [CommonModule, FormsModule, ChatFormatPipe],
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.scss'
})
export class ChatbotComponent implements OnInit, AfterViewChecked{

  @ViewChild('messagesContainer') messagesContainer!: ElementRef;
  @ViewChild('inputField') inputField!: ElementRef;

  // Estado del chat 
  estaAbierto       = false;
  estaCargando    = false;
  usuarioMensaje  = '';
  mensajes: ChatMensaje[] = [];

  nombreUsuario = '';
  usuarioRol = '';
  isAdmin = false;

  private readonly MAXIMO_HISTORIAL = 10;
  private shouldScrollToBottom = false;

  constructor(private chatService: ChatService, private authService: AuthService){

  }


  ngAfterViewChecked(): void {
    if (this.shouldScrollToBottom) {
      this.scrollToBottom();
      this.shouldScrollToBottom = false;
    }
  }

  ngOnInit(): void {
    // Obtener info del usuario autenticado
      const usuario = this.authService.usuario;

      this.nombreUsuario = usuario?.usuario?.nombreUsuario || 'Usuario';
      this.usuarioRol    = usuario?.isAdmin ? 'ADMIN' : 'ASOCIADO';
      this.isAdmin       = usuario?.isAdmin || false;

      // Mensaje de bienvenida
      this.mensajes.push({
        rol: 'assistant',
        contenido: this.obtenerMensajeBienvenida(),
        timestamp: new Date()
      });
  }

  toggleChat(): void {
    this.estaAbierto = !this.estaAbierto;
    if (this.estaAbierto) {
      this.shouldScrollToBottom = true;
      setTimeout(() => this.inputField?.nativeElement?.focus(), 100);
    }
  }

  closeChat(): void {
    this.estaAbierto = false;
  }

  enviarMensaje(): void {
    const msg = this.usuarioMensaje.trim();
    if (!msg || this.estaCargando) return;

    // Agregar mensaje del usuario
    this.mensajes.push({
      rol: 'user',
      contenido: msg,
      timestamp: new Date()
    });

    // Placeholder de carga
    const cargandoMensaje: ChatMensaje = {
      rol: 'assistant',
      contenido: '',
      timestamp: new Date(),
      loading: true
    };
    this.mensajes.push(cargandoMensaje);

    this.usuarioMensaje = '';
    this.estaCargando = true;
    this.shouldScrollToBottom = true;

    const history = this.buildHistory();

    // Llamar al backend
    this.chatService.sendMessage(msg, history).subscribe({
      next: (res) => {
        const idx = this.mensajes.indexOf(cargandoMensaje);
        if (idx !== -1) {
          this.mensajes[idx] = {
            rol: 'assistant',
            contenido: res.mensajeExitoso
              ? res.mensaje
              : (res.error || 'Error inesperado.'),
            timestamp: new Date(),
            loading: false
          };
        }
        this.estaCargando = false;
        this.shouldScrollToBottom = true;
      },
      error: (err) => {
        const idx = this.mensajes.indexOf(cargandoMensaje);
        if (idx !== -1) {
          this.mensajes[idx] = {
            rol: 'error',
            contenido: 'No se pudo conectar con el asistente. Intenta de nuevo.',
            timestamp: new Date(),
            loading: false
          };
        }
        this.estaCargando = false;
        this.shouldScrollToBottom = true;
      }
    });
  }

  onKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.enviarMensaje();
    }
  }

  limpiarChat(): void{
    this.mensajes = [{
      rol: 'assistant',
      contenido: this.obtenerMensajeBienvenida(),
      timestamp: new Date()
    }];
  }

   get obtenerSugerenciasRapidas(): string[] {
    if (this.isAdmin) {
      return [
        '¿Cuántos asociados activos hay?',
        '¿Cuál es el saldo total de ahorros?',
        '¿Cuántos préstamos están pendientes?',
        '¿Cuáles son las próximas actividades?'
      ];
    }
    return [
      '¿Cuál es mi saldo de ahorros?',
      '¿Cuánto es mi aporte mensual?',
      '¿Qué actividades hay disponibles?',
      '¿Qué beneficios tengo?'
    ];
  }

  useSuggestion(suggestion: string): void {
    this.usuarioMensaje = suggestion;
    this.enviarMensaje();
  }

  private obtenerMensajeBienvenida(): string {
    if (this.isAdmin) {
      return `¡Hola, ${this.nombreUsuario}! 👋 Soy el asistente de ASEFIDE. ` +
             `Como administrador puedes consultarme sobre asociados, ` +
             `ahorros, préstamos, actividades y más.`;
    }
    return `¡Hola, ${this.nombreUsuario}! 👋 Soy el asistente de ASEFIDE. ` +
           `Puedo ayudarte con tus ahorros, aportes, actividades y beneficios disponibles.`;
  }

   private buildHistory(): MensajeHistorial[] {
    return this.mensajes
      .filter(m => !m.loading)           // sin mensajes en carga
      .filter(m => m.rol !== 'error')    // sin mensajes de error
      .filter(m => m.contenido != null && m.contenido.trim() !== '') // sin nulls
      .slice(-this.MAXIMO_HISTORIAL)
      .map(m => ({
          rol: (m.rol === 'user' ? 'user' : 'assistant') as 'user' | 'assistant',
          content: m.contenido
      }));
  }
  private scrollToBottom(): void {
    try {
      const el = this.messagesContainer?.nativeElement;
      if (el) el.scrollTop = el.scrollHeight;
    } catch {}
  }

   formatTime(date: Date): string {
    return date.toLocaleTimeString('es-CR', {
      hour: '2-digit', minute: '2-digit'
    });
  }

}
