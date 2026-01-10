import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { AuthenticationService } from "../../services/services";
import { UserDto } from "../../services/models";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-register",
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: "./register.component.html",
  styleUrl: "./register.component.scss",
})
export class RegisterComponent implements OnInit {
  userDto: UserDto = {
    email: "",
    firstName: "",
    lastName: "",
    password: "",
    iban: "", 
  };

  errorMessages: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {}

  register() {
    this.errorMessages = [];
    this.authService
      .register({
        body: this.userDto,
      })
      .subscribe({
        next: async (data) => {
          await this.router.navigate(["confirm-register"]);
        },
        error: (err) => {
          this.errorMessages = err.error.validationErrors;
        },
      });
  }

  login() {
    this.router.navigate(["login"]);
  }
}
