import { Component, OnInit } from '@angular/core';
import { SensorService } from '../sensor.service';
import { LatestSensorReadings } from '../models/latest-sensor-readings.model';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  latestSensorReadings: LatestSensorReadings | null = null;
  isLoading: boolean = true;
  errorMessage: string = '';

  constructor(private sensorService: SensorService) {}

  ngOnInit(): void {
    this.loadLatestReadings();
  }

  loadLatestReadings(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.sensorService.getLatestReadings().subscribe({
      next: (data: LatestSensorReadings) => {
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
}
