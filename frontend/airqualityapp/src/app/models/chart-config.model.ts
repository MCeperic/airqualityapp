export interface ChartConfig {
    sensor: 'bme280' | 'pms5003' | 'scd40' | 'sgp40';
    measurement: string;
    timeRange: 'minute' | 'hourly' | 'daily';
    date?: string;
    hour?: number;
    startDate?: string;
    endDate?: string;
}
