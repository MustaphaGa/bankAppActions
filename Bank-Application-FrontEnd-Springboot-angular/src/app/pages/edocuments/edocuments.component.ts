import { Component, OnInit } from "@angular/core";
import { TransactionDto, UserDto } from "../../services/models";
import { TransactionService, UserService } from "../../services/services";
import { HelperService } from "../../services/helper/helper.service";
import { Router } from "@angular/router";
import * as pdfMake from "pdfmake/build/pdfmake";
import * as pdfFonts from "pdfmake/build/vfs_fonts";
import { serialize } from "v8";
import { text } from "stream/consumers";

(pdfMake as any).vfs = (pdfFonts as any).vfs;
@Component({
  selector: "app-edocuments",
  standalone: true,
  imports: [],
  templateUrl: "./edocuments.component.html",
  styleUrl: "./edocuments.component.scss",
})
export class EdocumentsComponent implements OnInit {
  userDto: UserDto = {
    email: "",
    firstName: "",
    lastName: "",
    password: "",
    iban: "",
  };
  transactions: TransactionDto[] = [];

  constructor(
    private router: Router,
    private userService: UserService,
    private helperService: HelperService,
    private transactionService: TransactionService
  ) {}

  ngOnInit(): void {
    this.userService
      .findById({ "user-id": this.helperService.userID })
      .subscribe((data) => {
        this.userDto = data;
        console.log(this.userDto);
      });
    this.transactionService
      .findAllByUserId({
        "user-id": this.helperService.userID,
      })
      .subscribe({
        next: (data) => {
          this.transactions = data;
          console.log(this.transactions);
        },
      });
  }

  generatePDF(action = "open") {
    const documentDefinition = this.getDocumentDefinition();

    switch (action) {
      case "open":
        pdfMake.createPdf(documentDefinition as any).open();
        break;
      case "print":
        pdfMake.createPdf(documentDefinition as any).print();
        break;
      case "download":
        pdfMake.createPdf(documentDefinition as any).download();
        break;
      default:
        pdfMake.createPdf(documentDefinition as any).open();
        break;
    }
  }

  getDocumentDefinition() {
    const currentDate = new Date();

    return {
      content: [
        {
          text: "Bank of Us ",
          color: "blue",
          fontSize: 15,
          width: 100,
          alignment: "center",
          margin: [0, 0, 0, 20],
        },
        {
          text: "La Date: " + currentDate.toLocaleDateString(),
          alignment: "left",
        },
        {
          text: "Attestation de R.I.B",
          bold: true,
          fontSize: 20,
          width: 100,
          alignment: "center",
          margin: [0, 0, 0, 20],
        },
        {
          text:
            " Dans le cadre de notre engagement envers notre clientèle, BancOfUs  a le plaisir" +
            " de vous remettre cette attestation de Relevé d'Identité Bancaire (RIB)." +
            "Nous restons à votre entière disposition et vous souhaitons un service des plus satisfaisants.,",
          size: 12,
        },

        {
          columns: [
            [
              {
                style: "tableExample",
                margin: [0, 50],
                table: {
                  widths: ["*", "*", 300],
                  body: [
                    ["Nom ", " Prenom ", "Numero de compte"],
                    [
                      {
                        text: [{ text: this.userDto.firstName, italics: true }],
                      },
                      {
                        text: [{ text: this.userDto.lastName, italics: true }],
                      },
                      {
                        text: [
                          {
                            text: this.userDto.iban,
                            italics: true,
                            color: "red",
                          },
                        ],
                      },
                    ],
                  ],
                },
              },

              {
                text:
                  "*************************************************" +
                  "\n" +
                  "Documents valider et vérifier par nos Services pour plus d'informations contacter nous." +
                  "\n",

                margin: [0, 280, 0, 0],
                alignment: "center",
              },

              {
                text: "Telephone: 0537333435" + "\n",
                margin: [0, 0, 0, 0],

                alignment: "center",
              },
              {
                text: "Email: BankOfUs@gmail.com" + "\n",
                margin: [0, 0, 0, 0],

                alignment: "center",
              },
              {
                text: "SiteWeb: WWW.BancOfUs.ma" + "\n",
                margin: [0, 0, 0, 0],

                alignment: "center",
              },
            ],
          ],
        },
      ],
    };
  }
  generateReleve(action = "open") {
    const documentReleve = this.getDocumentReleve();

    switch (action) {
      case "open":
        pdfMake.createPdf(documentReleve as any).open();
        break;
      case "print":
        pdfMake.createPdf(documentReleve as any).print();
        break;
      case "download":
        pdfMake.createPdf(documentReleve as any).download();
        break;
      default:
        pdfMake.createPdf(documentReleve as any).open();
        break;
    }
  }

  getDocumentReleve() {
    let body: any[] = [
      ["Date Operation", "TRANSFERT", "DEPOSIT"], // header row
    ];

    let totalTransfer = 0;
    let totalDeposit = 0;

    for (let transaction of this.transactions) {
      if (transaction.type === "TRANSFERT") {
        body.push([
          { text: `${transaction.transactionDate}`, italics: true },
          { text: `${transaction.amount} Dhs`, italics: true, color: "red" },
          { text: "", italics: true },
        ]);
        totalTransfer += transaction.amount ?? 0;
      } else if (transaction.type === "DEPOSIT") {
        body.push([
          { text: `${transaction.transactionDate}`, italics: true },
          { text: "", italics: true },
          { text: `${transaction.amount} Dhs`, italics: true, color: "green" },
        ]);
        totalDeposit += transaction.amount ?? 0;
      }
    }

    const balance = totalDeposit + totalTransfer;

    // Add Total row
    body.push([
      {
        text: "TOTAL",
        bold: true,
        color: "black",
        alignment: "right",
        colSpan: 1,
      },
      { text: `${totalTransfer} Dhs`, bold: true, color: "red" },
      { text: `${totalDeposit} Dhs`, bold: true, color: "green" },
    ]);

    // Add Balance row
    body.push([
      {
        text: "BALANCE",
        bold: true,
        color: "blue",
        alignment: "right",
        colSpan: 1,
      },
      { text: `${balance} Dhs`, bold: true, colSpan: 2, alignment: "center" },
      {}, // Empty cell because colSpan:2 starts from here
    ]);

    return {
      content: [
        {
          text: "Bank of Us",
          color: "blue",
          fontSize: 20,
          bold: true,
          alignment: "center",
          margin: [0, 0, 0, 20],
        },
        {
          text: "Relevé Bancaire",
          fontSize: 18,
          bold: true,
          alignment: "center",
          margin: [0, 0, 0, 10],
        },
        {
          text: `Nom Compte: ${this.helperService.fullName}`,
          margin: [0, 0, 0, 5],
        },
        {
          text: `Compte IBAN: ${this.userDto.iban}`,
          margin: [0, 0, 0, 5],
        },
        {
          text: `Date de relevé: ${new Date().toLocaleDateString()}`,
          margin: [0, 0, 0, 20],
        },
        {
          style: "tableExample",
          table: {
            widths: [120, "*", "*"],
            body: body,
          },
        },
        {
          text:
            "\n*************************************************\n" +
            "Document validé par nos services. Pour plus d'informations, contactez-nous.",
          margin: [0, 30, 0, 0],
          alignment: "center",
        },
        {
          text: "Téléphone: 0537333435",
          alignment: "center",
        },
        {
          text: "Email: BankOfUs@gmail.com",
          alignment: "center",
        },
        {
          text: "Site Web: www.BankOfUs.ma",
          alignment: "center",
        },
      ],
    };
  }
}
