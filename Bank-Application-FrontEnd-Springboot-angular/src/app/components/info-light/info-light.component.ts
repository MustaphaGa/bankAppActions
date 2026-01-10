import { NgClass } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';

export interface LightInfoInput {
  title?: String;
  amount?: number;
  styleInfo?: 'bg-primary' | 'bg-danger' | 'bg-warning' | 'bg-success';
}

@Component({
  selector: 'app-info-light',
  standalone: true,
  imports: [NgClass],
  templateUrl: './info-light.component.html',
  styleUrl: './info-light.component.scss',
})
export class InfoLightComponent implements OnInit {
  @Input() infoInput: LightInfoInput = {};
  constructor() {}
  ngOnInit(): void {}
}
