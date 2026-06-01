import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoAhorroFormComponent } from './tipo-ahorro-form.component';

describe('TipoAhorroFormComponent', () => {
  let component: TipoAhorroFormComponent;
  let fixture: ComponentFixture<TipoAhorroFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipoAhorroFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TipoAhorroFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
