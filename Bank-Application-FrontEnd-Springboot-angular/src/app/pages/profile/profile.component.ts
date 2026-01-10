import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { HelperService } from '../../services/helper/helper.service';
import { UserService } from '../../services/services';
import { UserDto } from '../../services/models';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent  implements OnInit{
  private location = inject(Location)
    userDto :UserDto = {
      email: '',
      firstName: '',
      lastName: '',
      password: ''
    };

  constructor(
    private router: Router,
   private userService:UserService,
      private helperService:HelperService) { }

  ngOnInit(): void {
     this.userService
    .findById({ 'user-id': this.helperService.userID })
    .subscribe((data) => {
      this.userDto = data;
    });
  }

goBack() {
this.location.back();
}
}
