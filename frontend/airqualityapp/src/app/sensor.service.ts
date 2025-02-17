import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SensorReading } from './models/sensor-reading.model';

@Injectable({
  providedIn: 'root'
})
export class SensorService {

  private apiUrl = 'http://localhost:8080/aqa/readings';

  constructor(private http: HttpClient) { }

  getReadings(): Observable<SensorReading[]> {
    return this.http.get<SensorReading[]>(this.apiUrl);
  }

  getReadingsByType(sensorType: string): Observable<any>{
    return this.http.get<any>('${this.apiUrl}/${sensorType}');
  }

  saveReading(sensorReading: SensorReading): Observable<SensorReading>{
    return this.http.post<SensorReading>(this.apiUrl, sensorReading);
  }
}
