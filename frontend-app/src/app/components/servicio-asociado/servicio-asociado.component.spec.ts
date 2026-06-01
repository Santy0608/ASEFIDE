import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicioAsociadoComponent } from './servicio-asociado.component';

describe('ServicioAsociadoComponent', () => {
  let component: ServicioAsociadoComponent;
  let fixture: ComponentFixture<ServicioAsociadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicioAsociadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicioAsociadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
