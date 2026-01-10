import { Component, OnInit } from '@angular/core';
import { RedirectCommand, Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';
import { AuthenticationRequest } from '../../services/models';
import { FormsModule, NgModel } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
  errorMessages: Array<string> = [];
  authRequest: AuthenticationRequest = {};

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}
  ngOnInit(): void {}

  login() {
    this.errorMessages = [];
    this.authService
      .authenticate({
        body: this.authRequest,
      })
      .subscribe({
        next: async (data: any) => {
          localStorage.setItem('token', data.token);
          const helper = new JwtHelperService();
          const decodedToken = helper.decodeToken(data.token);
          if (decodedToken.authorities[0].authority === 'ROLE_ADMIN') {
            await this.router.navigate(['admin/dashboard']);
          } else {
            await this.router.navigate(['user/dashboard']);
          }
        },
        error: (err) => {
          console.log(err);
          this.errorMessages.push(err.error.errorMessage);
        },
      });
      console.log(localStorage.getItem('token'))
  }
  async register() {
    await this.router.navigate(['register']);
  }
}
