import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InscripcionesActividadFormComponent } from './inscripciones-actividad-form.component';

describe('InscripcionesActividadFormComponent', () => {
  let component: InscripcionesActividadFormComponent;
  let fixture: ComponentFixture<InscripcionesActividadFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InscripcionesActividadFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InscripcionesActividadFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
