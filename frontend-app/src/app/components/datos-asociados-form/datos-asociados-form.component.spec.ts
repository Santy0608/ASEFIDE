import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatosAsociadosFormComponent } from './datos-asociados-form.component';

describe('DatosAsociadosFormComponent', () => {
  let component: DatosAsociadosFormComponent;
  let fixture: ComponentFixture<DatosAsociadosFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DatosAsociadosFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DatosAsociadosFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
