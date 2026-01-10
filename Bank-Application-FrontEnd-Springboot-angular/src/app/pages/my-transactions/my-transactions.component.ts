import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { StatisticService, TransactionService } from "../../services/services";
import { ContactDto, TransactionDto } from "../../services/models";
import { CommonModule } from "@angular/common";
import { HelperService } from "../../services/helper/helper.service";

@Component({
  selector: "app-my-transactions",
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: "./my-transactions.component.html",
  styleUrl: "./my-transactions.component.scss",
})
export class MyTransactionsComponent implements OnInit {
  transactions: Array<TransactionDto> = [];
  accountBalance = 0;

  constructor(
    private transactionService: TransactionService,
    private helperService: HelperService,
    private statisticService: StatisticService
  ) {}
  ngOnInit(): void {
    this.findAllByUserId();
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

  findAllByUserId() {
    this.transactionService
      .findAllByUserId({
        "user-id": this.helperService.userID,
      })
      .subscribe({
        next: (data) => {
          this.transactions = data;
        },
      });
  }
}
