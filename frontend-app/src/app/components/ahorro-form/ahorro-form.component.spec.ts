import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AhorroFormComponent } from './ahorro-form.component';

describe('AhorroFormComponent', () => {
  let component: AhorroFormComponent;
  let fixture: ComponentFixture<AhorroFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AhorroFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AhorroFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
