package com.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.MqttGateway;
import com.demo.entity.Device;
import com.demo.repository.DeviceRepository;

@Service
public class DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private MqttGateway mqtGateway;

	public void publishAndSave(String name, String value) throws Exception {
		Optional<Device> device = readDevice(name);
		if (device.isPresent()) {
			mqtGateway.sendToMqtt(value);
			save(name, value);
		} else {
			throw new Exception("Not device name: " + name);
		}
	}

	public void save(String name, String value) throws Exception {
		Optional<Device> device = readDevice(name);
		if (device.isPresent()) {
			device.get().setValue(value);
			deviceRepository.save(device.get());
			System.out.println("Device name: " + device.get().getName() + " save value: " + value);
		} else {
			throw new Exception("Not device name: " + name);
		}
	}

	public Device read(String name) throws Exception {
		Optional<Device> device = readDevice(name);
		if (device.isPresent()) {
			System.out.println("Device name: " + device.get().getName() + " read value: " + device.get().getValue());
			return device.get();
		} else {
			throw new Exception("Not device name: " + name);
		}
	}

	private Optional<Device> readDevice(String name) {
		Optional<Device> device = Optional.ofNullable(deviceRepository.findByName(name));
		return device;
	}
}
