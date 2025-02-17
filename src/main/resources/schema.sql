CREATE TABLE sensor_reading (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sensor_type VARCHAR(255) NOT NULL,
    sensor_value DOUBLE NOT NULL,
    timestamp TIMESTAMP NOT NULL
);