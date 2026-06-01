import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovimientosAhorroFormComponent } from './movimientos-ahorro-form.component';

describe('MovimientosAhorroFormComponent', () => {
  let component: MovimientosAhorroFormComponent;
  let fixture: ComponentFixture<MovimientosAhorroFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovimientosAhorroFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovimientosAhorroFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
