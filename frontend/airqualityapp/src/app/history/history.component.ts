import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { SensorReading } from '../models/sensor-reading.model';
import { SensorService } from '../sensor.service';
import { Chart, CategoryScale} from 'chart.js';
import 'chartjs-adapter-date-fns';

Chart.register(CategoryScale);


@Component({
  selector: 'app-history',
  standalone: false,
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit{
  @ViewChild('myChart') chartRef!: ElementRef;
  chart!: Chart;

  sensorTypes: string[] = ['PM2.5', 'PM10', 'NO2'];
  selectedSensor: string = 'PM2.5';
  selectedRange: string = '24h';
  historicalData: SensorReading[] = [];
  chartData: any;

  constructor(private sensorService: SensorService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.sensorService.getHistory(this.selectedSensor, this.selectedRange)
      .subscribe(data => {
        this.historicalData = data;
        this.createChart();
      })
  }

  createChart(): void {
    if (!this.historicalData.length) return;

    const ctx = this.chartRef.nativeElement.getContext('2d');
    
    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart(ctx, {
      type: 'line',
      data: {
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
      },
      options: {
        responsive: true,
        scales: {
          x: {
            title: {
              display: true,
              text: 'Time'
            }
          },
          y: {
            title: {
              display: true,
              text: this.selectedSensor
            }
          }
        }
      }
    });
  }

  onSensorChange(sensor: string): void{
    this.selectedSensor = sensor;
    this.fetchData();
  }

  onRangeChange(range: string): void {
    this.selectedRange = range;
    this.fetchData();
  }

}
