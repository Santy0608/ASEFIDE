import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InscripcionesActividadComponent } from './inscripciones-actividad.component';

describe('InscripcionesActividadComponent', () => {
  let component: InscripcionesActividadComponent;
  let fixture: ComponentFixture<InscripcionesActividadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InscripcionesActividadComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InscripcionesActividadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
