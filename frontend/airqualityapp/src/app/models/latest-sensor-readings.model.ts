import { Bme280Data } from "./bme280-data.model";
import { Pms5003Data } from "./pms5003-data.model";
import { Scd40Data } from "./scd40-data.model";
import { Sgp40Data } from "./sgp40-data.model";

export interface LatestSensorReadings {
    bme280Dto: Bme280Data;
    pms5003Dto: Pms5003Data;
    scd40Dto: Scd40Data;
    sgp40Dto: Sgp40Data;
}