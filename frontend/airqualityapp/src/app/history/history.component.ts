import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { SensorService } from '../sensor.service';
import { Chart, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, LineController} from 'chart.js';
import 'chartjs-adapter-date-fns';
import { ChartConfig } from '../models/chart-config.model';
import { SensorOption } from '../models/sensor-option.model';
import { SensorReadingsTimeSeries } from '../models/sensor-readings-time-series.model';
import { SensorReadings } from '../models/sensor-readings.model';
import { Bme280Data } from '../models/bme280-data.model';
import { Pms5003Data } from '../models/pms5003-data.model';
import { Scd40Data } from '../models/scd40-data.model';
import { Sgp40Data } from '../models/sgp40-data.model';

Chart.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, LineController);


@Component({
  selector: 'app-history',
  standalone: false,
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements AfterViewInit{
  @ViewChild('chart') chartRef!: ElementRef;
  chart!: Chart;

  config: ChartConfig = {
    sensor: 'bme280',
    measurement: 'temperature',
    timeRange: 'hourly',
    date: new Date().toISOString().split('T')[0]
  }

  sensorOptions: SensorOption[] = [
    {
      sensor: 'bme280',
      measurements: [
        { key: 'temperature', label: 'Temperatura', unit: '°C' },
        { key: 'humidity', label: 'Vlažnost', unit: '%' },
        { key: 'pressure', label: 'Pritisak', unit: 'hPa' }
      ]
    },
    {
      sensor: 'pms5003',
      measurements: [
        { key: 'pm1', label: 'PM1.0', unit: 'μg/m³' },
        { key: 'pm2_5', label: 'PM2.5', unit: 'μg/m³' },
        { key: 'pm10', label: 'PM10', unit: 'μg/m³' }
      ]
    },
    {
      sensor: 'scd40',
      measurements: [
        { key: 'co2', label: 'CO₂', unit: 'ppm' },
        { key: 'temperature', label: 'Temperatura', unit: '°C' },
        { key: 'humidity', label: 'Pritisak', unit: '%' }
      ]
    },
    {
      sensor: 'sgp40',
      measurements: [
        { key: 'vocIndex', label: 'VOC indeks', unit: '' }
      ]
    }
  ];

  hours = Array.from({length: 24}, (_, i) => i);
  isLoading = false;

  constructor(private sensorService: SensorService) {}

  ngAfterViewInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.isLoading = true;

    let dataObservable;

    switch (this.config.timeRange) {
      case 'minute':
        dataObservable = this.sensorService.getMinuteData(this.config.date!, this.config.hour!);
        break;
      case 'hourly':
        dataObservable = this.sensorService.getHourlyData(this.config.date!);
        break;
      case 'daily':
        dataObservable = this.sensorService.getDailyData(this.config.startDate!, this.config.endDate!);
        break;
    }

    dataObservable.subscribe({
      next: (response: SensorReadingsTimeSeries) => {
        this.createChart(response.data);
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading data:', error);
        this.isLoading = false;
      }
    });
  }

  createChart(data: SensorReadings[]): void {
    if (this.chart) {
      this.chart.destroy();
    }
    const chartData = this.prepareChartData(data);

    if (!chartData.labels?.length || !chartData.values?.length) {
    return; 
  }

    const ctx = this.chartRef.nativeElement.getContext('2d');

    this.chart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: chartData.labels,
        datasets: [
          {
            label: this.getSensorDisplayName(),
            data: chartData.values,
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderWidth: 2,
            fill: true
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            title: {
              display: true,
              text: this.getXAxisLabel()
            }
          },
          y: {
            title: {
              display: true,
              text: this.getCurrentMeasurementLabel()
            }
          }
        },
        plugins: {
          title: {
            display: true,
            text: `${this.getSensorDisplayName()}`,
            font: {
              size: 18,
              weight:'bold'
            }
          }
        }
      }
    });
  }

  prepareChartData(data: SensorReadings[]): { labels: string[], values: number[] } {
    const labels: string[] = [];
    const values: number[] = [];

    data.forEach(item => {
      labels.push(this.formatTimestamp(item.bme280Dto.timestamp));

      let value: number | null = null;

      switch (this.config.sensor) {
      case 'bme280':
        if (item.bme280Dto) {
          value = this.getBme280Value(item.bme280Dto, this.config.measurement);
        }
        break;
      case 'pms5003':
        if (item.pms5003Dto) {
          value = this.getPms5003Value(item.pms5003Dto, this.config.measurement);
        }
        break;
      case 'scd40':
        if (item.scd40Dto) {
          value = this.getScd40Value(item.scd40Dto, this.config.measurement);
        }
        break;
      case 'sgp40':
        if (item.sgp40Dto) {
          value = this.getSgp40Value(item.sgp40Dto, this.config.measurement);
        }
        break;
    }
    
    values.push(value || 0);
    })

    return {labels, values};
  }

  formatTimestamp(timestamp: string): string {
    const date = new Date(timestamp);
    switch (this.config.timeRange) {
      case 'minute': return date.toTimeString().substr(0, 5);
      case 'hourly': return date.getHours().toString().padStart(2, '0') + ':00';
      case 'daily': return date.toLocaleDateString('hr-HR', {
        day: '2-digit',
        month: 'numeric',
        year: 'numeric'
      });
      default: return date.toLocaleString();
    }
  }

  formatDisplayDate(dateStr: string): string {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    return `${day}.${month}.${year}`;
  }

  getXAxisLabel(): string {
    switch ( this.config.timeRange) {
      case 'minute': return 'Vrijeme';
      case 'hourly': return 'Vrijeme';
      case 'daily': return 'Datum';
      default: return 'Vrijeme';
    }
  }


  onSensorChange(): void{
    this.config.measurement = this.getCurrentSensorOptions()[0].key;
    this.fetchData();
  }

  onMeasurementChange(): void {
    this.fetchData();
  }

  onDateChange(): void {
    this.fetchData();
  }

  onTimeRangeChange(): void {
    const now = new Date();
    switch (this.config.timeRange) {
      case 'minute':
        if (!this.config.hour){
          this.config.hour = 9;
        }
        break;
      case 'hourly':
        break;
      case 'daily':
        if (!this.config.startDate) {
        this.config.startDate = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0];
        }
        if (!this.config.endDate) {
          this.config.endDate = now.toISOString().split('T')[0];
        }
        break;
    }
  }

  getCurrentSensorOptions() {
    return this.sensorOptions.find(s => s.sensor === this.config.sensor)?.measurements || [];
  }

  getCurrentMeasurementLabel(): string {
    const measurement = this.getCurrentSensorOptions().find(m => m.key === this.config.measurement);
    return measurement ? `${measurement.label} (${measurement.unit})` : '';
  }

  getSensorDisplayName(): string {
    switch (this.config.sensor) {
      case 'bme280': return 'BME280';
      case 'pms5003': return 'PMS5003'; 
      case 'scd40': return 'SCD40';
      case 'sgp40': return 'SGP40';
      default: return '';
    }
  }

  private getBme280Value(dto: Bme280Data, measurement: string): number | null {
    switch (measurement) {
      case 'temperature': return dto.temperature;
      case 'humidity': return dto.humidity;
      case 'pressure': return dto.pressure;
      default: return null;
    }
  }

  private getPms5003Value(dto: Pms5003Data, measurement: string): number | null {
    switch (measurement) {
      case 'pm1': return dto.pm1;
      case 'pm2_5': return dto.pm2_5;
      case 'pm10': return dto.pm10;
      default: return null;
    }
  }

  private getScd40Value(dto: Scd40Data, measurement: string): number | null {
    switch (measurement) {
      case 'co2': return dto.co2;
      case 'temperature': return dto.temperature;
      case 'humidity': return dto.humidity;
      default: return null;
    }
  }

  private getSgp40Value(dto: Sgp40Data, measurement: string): number | null {
    switch (measurement) {
      case 'vocIndex': return dto.vocIndex;
      default: return null;
    }
  }
}