import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuloReporteFormComponent } from './modulo-reporte-form.component';

describe('ModuloReporteFormComponent', () => {
  let component: ModuloReporteFormComponent;
  let fixture: ComponentFixture<ModuloReporteFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModuloReporteFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModuloReporteFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
