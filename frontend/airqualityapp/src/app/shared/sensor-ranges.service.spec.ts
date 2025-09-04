import { TestBed } from '@angular/core/testing';

import { SensorRangesService } from './sensor-ranges.service';

describe('SensorRangesService', () => {
  let service: SensorRangesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SensorRangesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
