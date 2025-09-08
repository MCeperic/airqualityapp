import { Component } from '@angular/core';

@Component({
  selector: 'app-sensor-info',
  standalone: false,
  templateUrl: './sensor-info.component.html',
  styleUrl: './sensor-info.component.css'
})
export class SensorInfoComponent {
  selectedSensor: string = 'bme280';

  onSensorSelect(sensorType: string) {
    this.selectedSensor = sensorType;
  }
}
