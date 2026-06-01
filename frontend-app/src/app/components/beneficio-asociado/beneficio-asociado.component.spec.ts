import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioAsociadoComponent } from './beneficio-asociado.component';

describe('BeneficioAsociadoComponent', () => {
  let component: BeneficioAsociadoComponent;
  let fixture: ComponentFixture<BeneficioAsociadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioAsociadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficioAsociadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
