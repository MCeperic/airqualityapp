#define ARDUINOJSON_USE_DOUBLE 0
#define ARDUINOJSON_ENABLE_STD_STRING 0
#define ARDUINOJSON_DECODE_UNICODE 0 

#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BME280.h>
#include <SensirionI2cScd4x.h>
#include <Adafruit_SGP40.h>
#include <Adafruit_PM25AQI.h>
#include <ArduinoJson.h>
#include <WiFi.h>
#include <HTTPClient.h>

const char* ssid = "****";
const char* password = "*********";

const char* serverURL = "http://192.168.1.162:8080/api/sensor-data";

Adafruit_BME280 bme280;
Adafruit_SGP40 sgp40;
Adafruit_PM25AQI  pms;
SensirionI2cScd4x scd40;
JsonDocument json;
HardwareSerial pmsSerial(2); 

void setup() {
  Serial.begin(115200);
  while (!Serial) delay(10);
  
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println();
  Serial.print("WiFi connected! IP address: ");
  Serial.println(WiFi.localIP());
  
  Wire.begin();
  
  Serial.print("Initializing BME280... ");
  if (!bme280.begin(0x76)) { 
    if (!bme280.begin(0x77)) { 
      Serial.println("Failed! Check wiring.");
      while (1) delay(10);
    }
  }
  Serial.println("Success!");
  
 
  Serial.print("Initializing SCD40... ");
  scd40.begin(Wire, 0x62);  
  
  uint16_t error = scd40.stopPeriodicMeasurement();
  if (error) {
    Serial.print("Error stopping measurement: ");
    Serial.println(error);
  }
  
  error = scd40.startPeriodicMeasurement();
  if (error) {
    Serial.println("Failed to start SCD40 measurement!");
    while (1) delay(10);
  }
  Serial.println("Success!");
  
  Serial.print("Initializing SGP40... ");
  if (!sgp40.begin()) {
    Serial.println("Failed! Check wiring.");
    while (1) delay(10);
  }
  Serial.println("Success!");
  
  pmsSerial.begin(9600, SERIAL_8N1, 16, 17); 

  if (!pms.begin_UART(&pmsSerial)) {
    Serial.println("Could not find PMS5003. Check wiring.");
    while (true) delay(10);
  }
  Serial.println("PMS5003 ready!");
  
  Serial.println("\n=== Sensor readings will start in 5 seconds ===\n");
  delay(5000);
}

void sendDataToBackend(String jsonData) {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    http.begin(serverURL);
    http.addHeader("Content-Type", "application/json");
    
    unsigned long start_time = millis();
    int httpResponseCode = http.POST(jsonData);
    unsigned long response_time = millis() - start_time;
    
    if (httpResponseCode > 0) {
      String response = http.getString();
      Serial.print("HTTP odgovor: ");
      Serial.println(httpResponseCode);
      Serial.print("Vrijeme odziva: ");
      Serial.print(response_time);
      Serial.println(" ms");
      Serial.print("RSSI: ");
      Serial.print(WiFi.RSSI());
      Serial.println(" dBm");
    } else {
      Serial.print("HTTP Error: ");
      Serial.println(httpResponseCode);
    }
    
    http.end();
  } else {
    Serial.println("WiFi not connected - cannot send data");
  }
}

void loop() {
  json.clear();

  float bme_temp = bme280.readTemperature();
  float bme_humidity = bme280.readHumidity();
  float bme_pressure = bme280.readPressure();

  if(!isnan(bme_temp) && !isnan(bme_humidity) && !isnan(bme_pressure)){
    json["bme280"]["temperature"] = bme_temp;
    json["bme280"]["humidity"] = bme_humidity;
    json["bme280"]["pressure"] = bme_pressure / 100.0F;
    json["bme280"]["status"] = "ok";
  } else {
    json["bme280"]["status"] = "error";
  }

  uint16_t scd_co2;
  float scd_temp, scd_humidity;  
  uint16_t error = scd40.readMeasurement(scd_co2, scd_temp, scd_humidity);
  
  if (error == 0) {
    json["scd40"]["co2"] = scd_co2;
    json["scd40"]["temp"] = scd_temp;
    json["scd40"]["humidity"] = scd_humidity;
    json["scd40"]["status"] = "ok";
  } else {
    json["scd40"]["status"] = "error";
  }

  uint16_t voc_index = sgp40.measureVocIndex(bme_temp, bme_humidity);
  uint16_t raw_gas = sgp40.measureRaw(bme_temp, bme_humidity);
  
  if(voc_index != 65535 && raw_gas != 65535){
    json["sgp40"]["voc_index"] = voc_index;
    json["sgp40"]["raw_gas"] = raw_gas;
    json["sgp40"]["status"] = "ok";
  } else {
    json["sgp40"]["status"] = "error";
  }

  PM25_AQI_Data data;
  if (pms.read(&data)) {     
    json["pms5003"]["pm1_0"] = data.pm10_standard;
    json["pms5003"]["pm2_5"] = data.pm25_standard;
    json["pms5003"]["pm10"] = data.pm100_standard;
    json["pms5003"]["status"] = "ok";
  } else {
    json["pms5003"]["status"] = "error";
  }

  String jsonString;
  serializeJson(json, jsonString);

  Serial.println(jsonString);

  sendDataToBackend(jsonString);

  delay(59300);
}
