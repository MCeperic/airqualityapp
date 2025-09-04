import { SensorReadings } from "./sensor-readings.model";

export interface SensorReadingsTimeSeries {
    data: SensorReadings[];
    timeRange: 'minute' | 'hourly' | 'daily';
    startTime: string;
    endTime: string;
}