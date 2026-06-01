import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PagosPrestamosFormComponent } from './pagos-prestamos-form.component';

describe('PagosPrestamosFormComponent', () => {
  let component: PagosPrestamosFormComponent;
  let fixture: ComponentFixture<PagosPrestamosFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PagosPrestamosFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PagosPrestamosFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
