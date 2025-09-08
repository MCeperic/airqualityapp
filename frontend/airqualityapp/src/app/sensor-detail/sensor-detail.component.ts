import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { SensorRangesService } from '../shared/sensor-ranges.service';

@Component({
  selector: 'app-sensor-detail',
  standalone: false,
  templateUrl: './sensor-detail.component.html',
  styleUrl: './sensor-detail.component.css'
})
export class SensorDetailComponent implements OnChanges {
  @Input() sensorType!: string;
  sensorInfo: any = {};
  readonly Infinity = Infinity;

  constructor(private sensorRangesService: SensorRangesService) {}

  sensorDatabase = {
    bme280: {
      name: 'BME280',
      image: 'assets/images/bme280.jpg',
      description: 'BME280 je senzor za mjerenje temperature zraka, relativne vlažnosti i tlaka zraka.'
    },
    pms5003: {
      name: 'PMS5003',
      image: 'assets/images/pms5003.jpg',
      description: 'PMS5003 je senzor koji mjeri koncentraciju lebdećih čestica u zraku. Može mjeriti čestice različitih veličina poput PM1, PM2.5 i PM10.',
      healthImportance: 'PM čestice u zraku su uzročnik respiratornih problema zbog mogućnosti ulaska duboko u pluća.'
    },
    scd40: {
      name: 'SCD40',
      image: 'assets/images/scd40.jpg',
      description: 'SCD40 je senzor za mjerenje koncentracije ugljikovog dioksida, temperature i vlažnosti',
      healthImportance: 'Visoke koncentracije CO₂ su indikator loše ventilacije te smanjuju kognitivne sposobnosti, uzrokuju umor i glavobolje.',
    },
    sgp40: {
      name: 'SGP40',
      image: 'assets/images/sgp40.jpg',
      description: 'SGP40 je senzor za mjerenje razine hlapljivih organskih spojeva u zraku.',
      healthImportance: 'Hlapivi organski spojevi mogu uzrokovati glavobolje, mučninu, iritaciju očiju i respiratornih organa.',
    }
  }

  ngOnChanges() {
      this.sensorInfo = this.sensorDatabase[this.sensorType as keyof typeof this.sensorDatabase];

      if (this.sensorInfo){
        this.attachMeasurement();
      }
  }

  private attachMeasurement() {
    const ranges = this.sensorRangesService.getSensorRanges();

    switch (this.sensorType) {
      case 'pms5003':
        this.sensorInfo!.measurements = [
          { parameter: 'PM2.5', unit: 'μg/m³', ranges: ranges.pm2_5 },
          { parameter: 'PM10', unit: 'μg/m³', ranges: ranges.pm10 }
        ];
        break;
      case 'scd40':
        this.sensorInfo!.measurements = [
          { parameter: 'CO₂', unit: 'ppm', ranges: ranges.co2 }
        ];
        break;
      case 'sgp40':
        this.sensorInfo!.measurements = [
          { parameter: 'VOC Indeks', unit: '', ranges: ranges.vocIndex }
        ];
        break;
      case 'bme280':
        this.sensorInfo!.measurements = [];
        break;
    }
  }
}
