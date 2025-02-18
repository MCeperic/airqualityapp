import { Component } from '@angular/core';
import { SensorService } from '../sensor.service';
import { SensorReading } from '../models/sensor-reading.model';


@Component({
  selector: 'app-sensor-form',
  standalone: false,
  templateUrl: './sensor-form.component.html',
  styleUrls: ['./sensor-form.component.css']
})
export class SensorFormComponent {
  sensorReading: SensorReading = {
    sensorType: '',
    value: 0,
    timestamp: new Date().toISOString()
  };

  constructor(private sensorService: SensorService) { }

  onSubmit() {
    this.sensorService.saveReading(this.sensorReading).subscribe (() => {
      console.log("Sensor reading saved!");
    });
  }
}
