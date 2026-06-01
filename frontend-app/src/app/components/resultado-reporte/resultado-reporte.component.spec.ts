import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultadoReporteComponent } from './resultado-reporte.component';

describe('ResultadoReporteComponent', () => {
  let component: ResultadoReporteComponent;
  let fixture: ComponentFixture<ResultadoReporteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadoReporteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultadoReporteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
