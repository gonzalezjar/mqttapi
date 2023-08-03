package com.example.mqttdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mqttdemo.entity.Device;



public interface DeviceRepository extends JpaRepository<Device, Long> {
	Device findByDevice(String deviceName);
}