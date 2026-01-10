import { Component } from '@angular/core';
import { MenuComponent } from "../../components/menu/menu.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main-admin',
  standalone: true,
  imports: [MenuComponent,RouterOutlet],
  templateUrl: './main-admin.component.html',
  styleUrl: './main-admin.component.scss'
})
export class MainAdminComponent {

}
