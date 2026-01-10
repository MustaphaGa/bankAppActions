import { Component, OnInit } from "@angular/core";
import { ContactDto, TransactionDto } from "../../services/models";
import {
  ContactService,
  StatisticService,
  TransactionService,
} from "../../services/services";
import { Router } from "@angular/router";
import { HelperService } from "../../services/helper/helper.service";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

@Component({
  selector: "app-new-transaction",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./new-transaction.component.html",
  styleUrl: "./new-transaction.component.scss",
})
export class NewTransactionComponent implements OnInit {
  transactionDto: TransactionDto = {};
  errorMessages: Array<string> = [];
  contacts: Array<ContactDto> = [];
  accountBalance = 0;
  selectedContact: ContactDto = {};
  iban: any;

  constructor(
    private transactionsService: TransactionService,
    private router: Router,
    private helperService: HelperService,
    private contactService: ContactService,
    private statisticService: StatisticService
  ) {}
  ngOnInit(): void {
    this.findAllContactByIdUser();
    this.statisticService
      .getAccountBalance({
        "user-id": this.helperService.userID,
      })
      .subscribe({
        next: (data) => {
          this.accountBalance = data;
        },
      });
  }

  onContactChange() {
    this.iban = this.selectedContact.iban;
    console.log(this.selectedContact.iban);
  }

  findAllContactByIdUser() {
    this.contactService
      .findAllByUserId1({
        "user-id": this.helperService.userID,
      })
      .subscribe({
        next: (data) => {
          this.contacts = data;
          console.log("contacts", this.contacts);
        },
      });
  }

  saveTransaction() {
    this.errorMessages = [];
    if (this.transactionDto.type == "TRANSFERT") {
      this.transactionDto.destinationIban = this.iban;
      this.transactionDto.contactId = this.selectedContact.id;
    }
    this.transactionDto.userId = this.helperService.userID;
    this.transactionsService
      .save1({
        body: this.transactionDto,
      })
      .subscribe({
        next: async () => {
          await this.router.navigate(["user/my-transaction"]);
        },
        error: (err) => {
          this.errorMessages = err.error.validationErrors;
          console.log(err.error.validationErrors);
        },
      });
  }
  
  async cancel() {
    await this.router.navigate(["user", "my-transaction"]);
  }
}
