import { Component, OnInit } from '@angular/core';
import { SensorService } from '../sensor.service';
import { SensorReading } from '../models/sensor-reading.model';

@Component({
  selector: 'app-sensor-list',
  standalone: false,
  templateUrl: './sensor-list.component.html',
  styleUrl: './sensor-list.component.css'
})
export class SensorListComponent implements OnInit{

  sensorReadings: SensorReading[] = [];

  constructor(private sensorService: SensorService) {}

  ngOnInit(): void {
    this.sensorService.getReadings().subscribe(
      (data) => {
        this.sensorReadings = data;
      },
      (error) => {
        console.error('Error fetching sensor readings:', error);
      }
    );
  }
}
