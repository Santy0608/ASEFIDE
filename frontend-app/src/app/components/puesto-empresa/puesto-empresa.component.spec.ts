import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PuestoEmpresaComponent } from './puesto-empresa.component';

describe('PuestoEmpresaComponent', () => {
  let component: PuestoEmpresaComponent;
  let fixture: ComponentFixture<PuestoEmpresaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PuestoEmpresaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PuestoEmpresaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
