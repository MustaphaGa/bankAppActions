import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EdocumentsComponent } from './edocuments.component';

describe('EdocumentsComponent', () => {
  let component: EdocumentsComponent;
  let fixture: ComponentFixture<EdocumentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EdocumentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EdocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
