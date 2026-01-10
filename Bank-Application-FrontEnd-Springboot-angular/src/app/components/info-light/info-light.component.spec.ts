import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoLightComponent } from './info-light.component';

describe('InfoLightComponent', () => {
  let component: InfoLightComponent;
  let fixture: ComponentFixture<InfoLightComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoLightComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoLightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
