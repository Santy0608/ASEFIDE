import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PuestoEmpresaFormComponent } from './puesto-empresa-form.component';

describe('PuestoEmpresaFormComponent', () => {
  let component: PuestoEmpresaFormComponent;
  let fixture: ComponentFixture<PuestoEmpresaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PuestoEmpresaFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PuestoEmpresaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
