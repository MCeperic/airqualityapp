import { Component, OnInit } from '@angular/core';
import { SensorTrend } from '../models/sensor-trend.model';
import { SensorService } from '../sensor.service';

@Component({
  selector: 'app-sensor-cards',
  standalone: false,
  templateUrl: './sensor-cards.component.html',
  styleUrl: './sensor-cards.component.css'
})
export class SensorCardsComponent implements OnInit{
  sensorTrends: SensorTrend[] = [];

  constructor(private sensorService: SensorService) {}

  ngOnInit(): void {
    this.sensorService.getReadingsWithTrends().subscribe((data) =>{
      this.sensorTrends = data;
    })
  }


  getTrendClass(trend: string): string {
    switch (trend) {
      case 'up': return 'trend-up';
      case 'down': return 'trend-down';
      default: return 'trend-steady';
    }
  }
}
