import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoAhorroComponent } from './tipo-ahorro.component';

describe('TipoAhorroComponent', () => {
  let component: TipoAhorroComponent;
  let fixture: ComponentFixture<TipoAhorroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipoAhorroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TipoAhorroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
