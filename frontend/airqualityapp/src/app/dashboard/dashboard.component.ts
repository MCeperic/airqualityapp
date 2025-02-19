import { Component, OnInit } from '@angular/core';
import { SensorService } from '../sensor.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  latestAQI: number = 0;

  constructor(private sensorService: SensorService) {}

  ngOnInit(): void {
    this.sensorService.getAQI().subscribe(
      (aqi: number) => {
        this.latestAQI = aqi;
        console.log('Latest AQI:', this.latestAQI);
      },
      (error) => {
        console.error('Error fetching AQI', error);
      }
    );
  }



}
