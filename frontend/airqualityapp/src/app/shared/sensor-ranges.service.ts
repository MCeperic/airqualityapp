import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SensorRangesService {

  getSensorRanges() {
    return {
    pm2_5: [
      { level: 'Odlično', min: 0, max: 5, cssClass: 'excellent' },
      { level: 'Dobro', min: 6, max: 15, cssClass: 'good' },
      { level: 'Umjereno', min: 16, max: 50, cssClass: 'moderate' },
      { level: 'Loše', min: 51, max: 90, cssClass: 'poor' },
      { level: 'Jako loše', min: 91, max: 140, cssClass: 'very-poor' },
      { level: 'Ekstremno loše', min: 141, max: Infinity, cssClass: 'extremely-poor' },
    ],
    pm10: [
      { level: 'Odlično', min: 0, max: 15, cssClass: 'excellent' },
      { level: 'Dobro', min: 16, max: 45, cssClass: 'good' },
      { level: 'Umjereno', min: 46, max: 120, cssClass: 'moderate' },
      { level: 'Loše', min: 121, max: 195, cssClass: 'poor' },
      { level: 'Jako loše', min: 196, max: 270, cssClass: 'very-poor' },
      { level: 'Ekstremno loše', min: 271, max: Infinity, cssClass: 'extremely-poor' },
    ],
    co2: [
      { level: 'Odlično', min: 0, max: 400, cssClass: 'excellent' },
      { level: 'Dobro', min: 401, max: 1000, cssClass: 'good' },
      { level: 'Umjereno', min: 1001, max: 2000, cssClass: 'moderate' },
      { level: 'Loše', min: 2001, max: Infinity, cssClass: 'poor' }
    ],
    vocIndex: [
      { level: 'Odlično', min: 0, max: 100, cssClass: 'excellent' },
      { level: 'Dobro', min: 101, max: 200, cssClass: 'good' },
      { level: 'Umjereno', min: 201, max: 250, cssClass: 'moderate' },
      { level: 'Loše', min: 251, max: 350, cssClass: 'poor' },
      { level: 'Jako loše', min: 351, max: Infinity, cssClass: 'very-poor' }
    ]
  };
  }
}
