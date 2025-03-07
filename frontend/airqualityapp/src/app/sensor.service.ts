import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SensorReading } from './models/sensor-reading.model';
import { SensorTrend } from './models/sensor-trend.model';

@Injectable({
  providedIn: 'root'
})
export class SensorService {

  private apiUrl = 'http://localhost:8080/aqa';

  constructor(private http: HttpClient) { }

  getReadings(): Observable<SensorReading[]> {
    return this.http.get<SensorReading[]>(`${this.apiUrl}/readings`);
  }

  getReadingsByType(sensorType: string): Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/readings/${sensorType}`);
  }

  getAQI(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/dashboard/aqi`);
  }

  getReadingsWithTrends(): Observable<SensorTrend[]> {
    return this.http.get<SensorTrend[]>(`${this.apiUrl}/dashboard/cards`)
  }

  getHistory(sensorType: string, range: string): Observable<SensorReading[]> {
    return this.http.get<SensorReading[]>(`${this.apiUrl}/history?sensorType=${sensorType}&range=${range}`);
  }
}
