import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultadoReporteFormComponent } from './resultado-reporte-form.component';

describe('ResultadoReporteFormComponent', () => {
  let component: ResultadoReporteFormComponent;
  let fixture: ComponentFixture<ResultadoReporteFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadoReporteFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultadoReporteFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
