import { Component } from '@angular/core';
import { NavbarComponent } from '../components/navbar/navbar.component';
import { RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  imports: [NavbarComponent, RouterModule, RouterLink],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.css'
})
export class MainLayoutComponent {

}
