import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LugarEventoFormComponent } from './lugar-evento-form.component';

describe('LugarEventoFormComponent', () => {
  let component: LugarEventoFormComponent;
  let fixture: ComponentFixture<LugarEventoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LugarEventoFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LugarEventoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
