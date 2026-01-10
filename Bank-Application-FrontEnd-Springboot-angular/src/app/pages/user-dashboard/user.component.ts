import { Component, NgModule, ViewChild } from '@angular/core';
import {
  InfoLightComponent,
  LightInfoInput,
} from '../../components/info-light/info-light.component';
import { CommonModule, DatePipe, NgFor } from '@angular/common';
import { StatisticService, TransactionService } from '../../services/services';
import { HelperService } from '../../services/helper/helper.service';
import { lastValueFrom } from 'rxjs';
import { BaseChartDirective, NgChartsModule } from 'ng2-charts';
import { ChartConfiguration } from 'chart.js';
import { DatepickerModule, DatepickerOptions } from 'ng2-datepicker';
import { FormsModule } from '@angular/forms';



@Component({
  selector: 'app-user',
  standalone: true,
  imports: [
    NgFor,
    FormsModule,
    NgChartsModule,
    CommonModule,
    InfoLightComponent,
    
  ],
  providers: [DatePipe],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
})

export class UserComponent {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  accountInfoList: Array<LightInfoInput> = [];
  accountBalance = 0;
  highestTransfer = 0;
  highestDeposit = 0;
  chartType: 'bar' = 'bar';
  chartData: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: [
      {
        label: 'Amount by Date',
        data: [],
        backgroundColor: 'rgb(13, 64, 205)',
      },
    ],
  };

  chartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'bottom',
      },
    },
    
  };
  dateOptions: DatepickerOptions = {
    format: 'yyyy-MM-dd',
  };
  startDate: Date = new Date();
  endDate: Date = new Date();
  
  constructor(
    private statisticService: StatisticService,
    private helperService: HelperService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.intialiseAccountInfo();
  }

  loadChartData() {
    
    const start = this.datePipe.transform(this.startDate, 'yyyy-MM-dd');
    const end = this.datePipe.transform(this.endDate, 'yyyy-MM-dd');

    this.statisticService
      .findSumTransactionByDate({
        'start-date': start!,
        'end-date': end!,
        'user-id': this.helperService.userID,
      })
      .subscribe((data) => {
        console.log('data:',data)
        const labels = data.map((t) =>
          this.datePipe.transform(t.transactionDate, 'dd/MM/yyyy')
              );
        const values = data
          .map((t) => t.amount)
          .filter((amount): amount is number => amount !== undefined);
        this.chartData.labels = labels;
        this.chartData.datasets[0].data = values;
        this.chart?.update();
        console.log('Chart labels:', labels);
        console.log('Chart data:', values);
      });
  }

  private async intialiseAccountInfo() {
    this.accountBalance = await lastValueFrom(
      this.statisticService.getAccountBalance({
        'user-id': this.helperService.userID,
      })
    );
    this.highestTransfer = await lastValueFrom(
      this.statisticService.highestTransfert({
        'user-id': this.helperService.userID,
      })
    );
    this.highestDeposit = await lastValueFrom(
      this.statisticService.highestDeposit({
        'user-id': this.helperService.userID,
      })
    );

    this.accountInfoList = [
      {
        title: 'Account Balance',
        amount: this.accountBalance,
        styleInfo: 'bg-primary',
      },
      {
        title: 'Highest transfer',
        amount: this.highestTransfer,
        styleInfo: 'bg-success',
      },
      {
        title: ' Highest deposit',
        amount: this.highestDeposit,
        styleInfo: 'bg-danger',
      },
    ];
  }
}
