import { Component, OnInit } from '@angular/core';
import { SensorService } from '../sensor.service';
import { SensorReading } from '../models/sensor-reading.model';
import { Chart, TimeScale, LineController, LineElement, PointElement, LinearScale } from 'chart.js';
import 'chartjs-adapter-date-fns';

Chart.register(TimeScale, LineController, LineElement, PointElement, LinearScale);

@Component({
  selector: 'app-sensor-list',
  standalone: false,
  templateUrl: './sensor-list.component.html',
  styleUrl: './sensor-list.component.css'
})
export class SensorListComponent implements OnInit{

  sensorReadings: SensorReading[] = [];
  chart: any;

  constructor(private sensorService: SensorService) {}

  ngOnInit(): void {
    this.sensorService.getReadings().subscribe(
      (data) => {
        this.sensorReadings = data;
        this.createChart();
      },
      (error) => {
        console.error('Error fetching sensor readings:', error);
      }
    );
  }

  createChart(): void{
    if(!this.sensorReadings || this.sensorReadings.length === 0){
      console.log('No data avalible to create chart.');
      return;
    }

    const timestamps = this.sensorReadings.map((entry: any) => entry.timestamp);
    const co2Values = this.sensorReadings.filter((entry: any) => entry.sensorType === 'CO2').map((entry: any) => entry.value);
    const pm25Values = this.sensorReadings.filter((entry: any) => entry.sensorType === 'PM2.5').map((entry: any) => entry.value);
    const pm10Values = this.sensorReadings.filter((entry: any) => entry.sensorType === 'PM10').map((entry: any) => entry.value);

    console.log('Timestamps:', timestamps);
    console.log('CO2 Values:', co2Values);
    console.log('PM2.5 Values:', pm25Values);
    console.log('PM10 Values:', pm10Values);
    console.log(this.sensorReadings);
    

    this.chart = new Chart('sensorChart', {
      type: 'line',
      data: {
        labels: timestamps,
        datasets: [
          {
            label: 'CO2 Levels',
            data: co2Values,
            borderColor: 'rgba(255, 99, 132, 1)',
            fill: false
          },
          {
            label: 'PM2.5 Levels',
            data: pm25Values,
            borderColor: 'rgba(54, 162, 235, 1)',
            fill: false
          },
          {
            label: 'PM10 Levels',
            data: pm10Values,
            borderColor: 'rgba(75, 192, 192, 1)',
            fill: false
          }
        ]
      },
      options: {
        scales: {
          x: {
            type: 'time',
            time: {
              unit: 'minute'
            },
            title:  {
              display: true,
              text: 'Timestamp'
            }
          },
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Sensor Value'
            }
          }
        }
      }
    });
  }
}
