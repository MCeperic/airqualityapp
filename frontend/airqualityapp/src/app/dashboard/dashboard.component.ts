import { Component, OnInit } from '@angular/core';
import { SensorService } from '../sensor.service';
import { SensorReadings } from '../models/sensor-readings.model';
import { SensorRangesService } from '../shared/sensor-ranges.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  latestSensorReadings: SensorReadings | null = null;
  isLoading: boolean = true;
  errorMessage: string = '';

  constructor(private sensorService: SensorService, private sensorRangesService: SensorRangesService) {}

  ngOnInit(): void {
    this.loadLatestReadings();
  }

  loadLatestReadings(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.sensorService.getLatestReadings().subscribe({
      next: (data: SensorReadings) => {
        console.log('Recieved data: ', data);
        this.latestSensorReadings = data;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load sensor data';
        this.isLoading = false;
      }
    })
  }

  getStatus(value: number, sensorType: string): { level: string, cssClass: string } {
    const allRanges = this.sensorRangesService.getSensorRanges();
    const ranges = allRanges[sensorType as keyof typeof allRanges];
    if (!ranges) return { level: 'N/A', cssClass: 'unknown' };

    for (const range of ranges) {
      if (value >= range.min && value <= range.max) {
        return { level: range.level, cssClass: range.cssClass };
      }
    }
    
    return { level: 'N/A', cssClass: 'unknown' };
  }

  formatTimestamp(): string {
    if (!this.latestSensorReadings?.bme280Dto?.timestamp) {
      return 'N/A';
    }
    
    const date = new Date(this.latestSensorReadings.bme280Dto.timestamp);
    return date.toLocaleString('hr-HR', {
      day: '2-digit',
      month: '2-digit', 
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    });
  }

  getPM1Status() {
    return this.getStatus(this.latestSensorReadings?.pms5003Dto?.pm1 || 0, 'pm1');
  }

  getPM25Status() {
    return this.getStatus(this.latestSensorReadings?.pms5003Dto?.pm2_5 || 0, 'pm2_5');
  }

  getPM10Status() {
    return this.getStatus(this.latestSensorReadings?.pms5003Dto?.pm10 || 0, 'pm10');
  }

  getCO2Status() {
    return this.getStatus(this.latestSensorReadings?.scd40Dto?.co2 || 0, 'co2');
  }

  getVOCStatus() {
    return this.getStatus(this.latestSensorReadings?.sgp40Dto?.vocIndex || 0, 'vocIndex');
  }

  getTemperatureStatus() {
    return this.getStatus(this.latestSensorReadings?.bme280Dto?.temperature || 0, 'temperature');
  }

  getHumidityStatus() {
    return this.getStatus(this.latestSensorReadings?.bme280Dto?.humidity || 0, 'humidity');
  }

  getPressureStatus() {
    return this.getStatus(this.latestSensorReadings?.bme280Dto?.pressure || 0, 'pressure');
  }
}
