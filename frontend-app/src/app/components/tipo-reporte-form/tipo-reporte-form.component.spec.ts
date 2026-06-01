import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoReporteFormComponent } from './tipo-reporte-form.component';

describe('TipoReporteFormComponent', () => {
  let component: TipoReporteFormComponent;
  let fixture: ComponentFixture<TipoReporteFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipoReporteFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TipoReporteFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
