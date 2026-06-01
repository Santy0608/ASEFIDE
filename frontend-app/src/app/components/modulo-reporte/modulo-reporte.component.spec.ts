import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuloReporteComponent } from './modulo-reporte.component';

describe('ModuloReporteComponent', () => {
  let component: ModuloReporteComponent;
  let fixture: ComponentFixture<ModuloReporteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModuloReporteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModuloReporteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
