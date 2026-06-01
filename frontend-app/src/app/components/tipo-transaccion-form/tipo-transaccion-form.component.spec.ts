import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoTransaccionFormComponent } from './tipo-transaccion-form.component';

describe('TipoTransaccionFormComponent', () => {
  let component: TipoTransaccionFormComponent;
  let fixture: ComponentFixture<TipoTransaccionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipoTransaccionFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TipoTransaccionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
