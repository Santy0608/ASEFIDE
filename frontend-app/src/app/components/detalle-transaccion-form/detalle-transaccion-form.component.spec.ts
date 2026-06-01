import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleTransaccionFormComponent } from './detalle-transaccion-form.component';

describe('DetalleTransaccionFormComponent', () => {
  let component: DetalleTransaccionFormComponent;
  let fixture: ComponentFixture<DetalleTransaccionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleTransaccionFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleTransaccionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
