import { NgIf } from '@angular/common';
import { Component, input, Input, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService, UserService } from '../../services/services';
import { HelperService } from '../../services/helper/helper.service';
import { UserDto } from '../../services/models';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterModule,NgIf,NgbDropdownModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss',
})
export class MenuComponent implements OnInit {
  @Input() isAdmin = false;
  role = 'user';
  userDto :UserDto = {
    email: '',
    firstName: '',
    lastName: '',
    password: '',
    iban: '',
  };

  constructor(
    private authService:AuthenticationService,
    private userService:UserService,
    private helperService:HelperService,
  private router:Router) { }

  ngOnInit(): void {
    this.userService
    .findById({ 'user-id': this.helperService.userID })
    .subscribe((data) => {
      this.userDto = data;
    });


    if (this.isAdmin) {
      this.role = 'admin';
    }
  }
  logout(){
    console.log('deconnecter')
    localStorage.removeItem('token');
    
    this.router.navigate(['login']);
  }
}
