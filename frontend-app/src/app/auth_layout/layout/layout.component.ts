import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-layout',
  imports: [RouterModule, RouterLink, CommonModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

}
