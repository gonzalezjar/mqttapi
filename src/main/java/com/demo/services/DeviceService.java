package com.demo.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.MqttGateway;
import com.demo.entity.Device;
import com.demo.repository.DeviceRepository;

@Service
public class DeviceService {
	
	private static Logger log = LoggerFactory.getLogger(DeviceService.class);
	
	@Value("${mqtt.topic.pub}")
	private String topic_pub;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private MqttGateway mqtGateway;

	public void publishAndSave(String deviceName, String value) throws Exception {
		Optional<Device> device = readDevice(deviceName);
		if (device.isPresent()) {
			mqtGateway.sendToMqtt(value, topic_pub + "/" + deviceName);
			save(deviceName, value);
		} else {
			throw new Exception("Not device name: " + deviceName);
		}
	}

	public void save(String deviceName, String value) throws Exception {
		Optional<Device> device = readDevice(deviceName);
		if (device.isPresent()) {
			device.get().setValue(value);
			deviceRepository.save(device.get());
			log.info("Device name: " + device.get().getDevice() + " save value: " + value);
		} else {
			throw new Exception("Not device name: " + deviceName);
		}
	}

	public Device read(String deviceName) throws Exception {
		Optional<Device> device = readDevice(deviceName);
		if (device.isPresent()) {
			log.info("Device name: " + device.get().getDevice() + " read value: " + device.get().getValue());
			return device.get();
		} else {
			throw new Exception("Not device name: " + deviceName);
		}
	}

	private Optional<Device> readDevice(String deviceName) {
		Optional<Device> device = Optional.ofNullable(deviceRepository.findByDevice(deviceName));
		return device;
	}
}
