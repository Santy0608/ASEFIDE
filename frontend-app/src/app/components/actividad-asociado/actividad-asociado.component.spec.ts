import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActividadAsociadoComponent } from './actividad-asociado.component';

describe('ActividadAsociadoComponent', () => {
  let component: ActividadAsociadoComponent;
  let fixture: ComponentFixture<ActividadAsociadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActividadAsociadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActividadAsociadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
