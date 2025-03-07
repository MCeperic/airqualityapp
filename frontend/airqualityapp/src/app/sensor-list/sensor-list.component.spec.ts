import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SensorListComponent } from './sensor-list.component';

describe('SensorListComponent', () => {
  let component: SensorListComponent;
  let fixture: ComponentFixture<SensorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SensorListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SensorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
