import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelefonoFormComponent } from './telefono-form.component';

describe('TelefonoFormComponent', () => {
  let component: TelefonoFormComponent;
  let fixture: ComponentFixture<TelefonoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelefonoFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TelefonoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
