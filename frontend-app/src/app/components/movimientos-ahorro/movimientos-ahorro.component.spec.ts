import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovimientosAhorroComponent } from './movimientos-ahorro.component';

describe('MovimientosAhorroComponent', () => {
  let component: MovimientosAhorroComponent;
  let fixture: ComponentFixture<MovimientosAhorroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovimientosAhorroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovimientosAhorroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
