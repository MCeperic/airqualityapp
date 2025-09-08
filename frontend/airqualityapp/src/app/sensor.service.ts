import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SensorReadings } from './models/sensor-readings.model';
import { SensorReadingsTimeSeries } from './models/sensor-readings-time-series.model';

@Injectable({
  providedIn: 'root'
})
export class SensorService {

  private apiUrl = 'http://localhost:8080/aqa';

  constructor(private http: HttpClient) { }

  getLatestReadings():Observable<SensorReadings> {
    return this.http.get<SensorReadings>(`${this.apiUrl}/dashboard/latestReadings`);
  }

  getMinuteData(date: string, hour: number): Observable<SensorReadingsTimeSeries> {
    return this.http.get<SensorReadingsTimeSeries>(`${this.apiUrl}/history/minute?date=${date}&hour=${hour}`);
  }

  getHourlyData(date: string): Observable<SensorReadingsTimeSeries> {
    return this.http.get<SensorReadingsTimeSeries>(`${this.apiUrl}/history/hourly?date=${date}`);
  }

  getDailyData(startDate: string, endDate: string): Observable<SensorReadingsTimeSeries> {
    return this.http.get<SensorReadingsTimeSeries>(`${this.apiUrl}/history/daily?startDate=${startDate}&endDate=${endDate}`);
  }
}
