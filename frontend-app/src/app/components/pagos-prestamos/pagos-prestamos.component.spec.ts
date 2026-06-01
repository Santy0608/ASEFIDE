import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PagosPrestamosComponent } from './pagos-prestamos.component';

describe('PagosPrestamosComponent', () => {
  let component: PagosPrestamosComponent;
  let fixture: ComponentFixture<PagosPrestamosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PagosPrestamosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PagosPrestamosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
