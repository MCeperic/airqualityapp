import { Component, OnInit } from '@angular/core';
import { SensorReading } from '../models/sensor-reading.model';
import { SensorService } from '../sensor.service';
import { Chart, TimeScale, LineController, LineElement, PointElement, LinearScale } from 'chart.js';


@Component({
  selector: 'app-history',
  standalone: false,
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit{
  sensorTypes: string[] = ['PM2.5', 'PM10', 'NO2'];
  selectedSensor: string = 'PM2.5';
  selectedRange: string = '24h';
  historicalData: SensorReading[] = [];
  chartData: any;

  constructor(private sensorService: SensorService) {}

  ngOnInit(): void {
    this.fetchData;
  }

  fetchData(): void {
    this.sensorService.getHistory(this.selectedSensor, this.selectedRange)
      .subscribe(data => {
        this.historicalData = data;
        this.prepareGraphData();
      })
  }

  prepareGraphData(): void {
    if(!this.historicalData.length) return;

    this.chartData = {
      labels: this.historicalData.map(entry => new Date(entry.timestamp).toLocaleString()),
      datasets: [
        {
          label: `${this.selectedSensor} Levels`,
          data: this.historicalData.map(entry => entry.value),
          borderColor: 'rgba(75, 192, 192, 1)',
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderWidth: 2
        }
      ]
    }
  }

  onSensorChange(sensor: string): void{
    this.selectedSensor = sensor;
    this.fetchData;
  }

  onRangeChange(range: string): void {
    this.selectedRange = range;
    this.fetchData;
  }

}
