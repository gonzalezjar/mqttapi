package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
	Device findByName(String name);
}