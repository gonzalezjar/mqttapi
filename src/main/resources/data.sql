CREATE TABLE IF NOT EXISTS devices (
  id INT PRIMARY KEY,
  device VARCHAR(50),
  value VARCHAR(50)
);
INSERT INTO devices (id, device, value) VALUES (198456, 'sensor_1', '0');
INSERT INTO devices (id, device, value) VALUES (198457, 'sensor_2', '0');
INSERT INTO devices (id, device, value) VALUES (234567, 'relay_1', 'off');
INSERT INTO devices (id, device, value) VALUES (234568, 'relay_2', 'off');