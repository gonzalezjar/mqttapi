CREATE TABLE IF NOT EXISTS devices (
  id INT PRIMARY KEY,
  name VARCHAR(50),
  value VARCHAR(50)
);
INSERT INTO devices (id, name, value) VALUES (198456, 'Sensor1', '0');
INSERT INTO devices (id, name, value) VALUES (234567, 'Led1', 'off');