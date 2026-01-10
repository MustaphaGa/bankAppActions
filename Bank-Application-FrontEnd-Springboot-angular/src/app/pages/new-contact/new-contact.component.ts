import { Component, NgModule, OnInit } from '@angular/core';
import { ContactService } from '../../services/services';
import { ContactDto } from '../../services/models';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { HelperService } from '../../services/helper/helper.service';

@Component({
  selector: 'app-new-contact',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './new-contact.component.html',
  styleUrl: './new-contact.component.scss',
})
export class NewContactComponent implements OnInit {
  contactDto: ContactDto = {
    email: '',
    firstName: '',
    lastName: '',
  };
  errorMessages: Array<string> = [];
  constructor(
    private contactService: ContactService,
    private router: Router,
    private helperService: HelperService,
    private activatedRoute: ActivatedRoute
  ) { }
  ngOnInit(): void {
    const contactId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    if (contactId) {
      this.contactService
        .findById2({
          'contact-id': contactId,
        })
        .subscribe({
          next: (data) => {
            this.contactDto = data;
          },
          error: (err) => {
            console.error('Failed to load contact:', err);
          },
        });
    }
  }
  createContact() {
    this.errorMessages = [];
    this.contactDto.userId = this.helperService.userID;
    this.contactService.save2({ body: this.contactDto }).subscribe({
      next: async () => {
        await this.router.navigate(['user/my-contact']);
      },
      error: (err) => {
        this.errorMessages = err.error.validationErrors;
      },
    });
  }
  cancel() {
    this.router.navigate(['user/my-contact']);
  }
}
