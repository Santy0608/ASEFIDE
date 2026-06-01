import { Component } from '@angular/core';
import { RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ChatbotComponent } from './components/chatbot/chatbot.component';
import { AuthService } from './services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent, RouterLink, RouterModule, ChatbotComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend-app';

  constructor(private authService: AuthService){

  }

  get isAuthenticated(): boolean {
    return this.authService.authenticated();
  }

}
