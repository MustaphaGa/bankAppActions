import { Component, NgModule, OnInit } from "@angular/core";
import { UserDto } from "../../services/models";
import { UserService } from "../../services/services";
import { CommonModule } from "@angular/common";
import { FormsModule, NgModel } from "@angular/forms";

@Component({
  selector: "app-users-manager",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./users-manager.component.html",
  styleUrl: "./users-manager.component.scss",
})
export class UsersManagerComponent implements OnInit {
  clients: Array<UserDto> = [];
  ShowOnlyInactive = false;
  userIdToUpdate = -1;
  updateState: boolean | undefined;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.findAllClients();
  }
  findAllClients() {
    this.userService.findAll().subscribe({
      next: (data) => {
        this.clients = data;
      },
    });
  }

  filterClients() {
    if (this.ShowOnlyInactive) {
      this.clients = this.clients.filter((client) => !client.active);
    } else {
      this.findAllClients();
    }
  }

  changeUserState(active: boolean | undefined, id: number | undefined) {
    this.userIdToUpdate = id as number;
    this.updateState = active;
  }

  updateUserState() {
    if (this.updateState) {
      this.userService
        .validateAccount({
          "user-id": this.userIdToUpdate as number,
        })
        .subscribe({
          next: () => {
            this.findAllClients();
          },
        });
    } else {
      this.userService
        .invalidateAccount({
          "user-id": this.userIdToUpdate as number,
        })
        .subscribe({
          next: () => {},
        });
    }
  }

  cancelUpdate() {
    const client = this.clients.find((c) => c.id === this.userIdToUpdate);
    if (client) {
      client.active = !client.active;
    }
    this.userIdToUpdate = -1;
    this.updateState = undefined;
  }
}
