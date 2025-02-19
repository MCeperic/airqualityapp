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

  getAQIPercentage(): number {
    switch (this.latestAQI) {
      case 1: return 100;
      case 2: return 80;
      case 3: return 60;
      case 4: return 40;
      case 5: return 20;
      case 6: return 10;
      default: return 0;
    }
  }

  getAQIColor(): string {
    switch (this.latestAQI) {
      case 1: return '#006400'; //Dark green
      case 2: return '#008000'; //Green
      case 3: return '#FFFF00'; //Orange
      case 4: return '#FFA500'; //Dark orange
      case 5: return '#FF4500'; //Red
      case 6: return '#800080'; //Purple
      default: return '#D3D3D3'; //Gray
    }
  }
  
  getAQIText(): string {
    switch (this.latestAQI) {
      case 1: return "Jako dobro";
      case 2: return "Dobro";
      case 3: return "Umjereno";
      case 4: return "Loše";
      case 5: return "Jako loše";
      case 6: return "Ekstremno loše";
      default: return "Nepoznato";
    }
  }
}
