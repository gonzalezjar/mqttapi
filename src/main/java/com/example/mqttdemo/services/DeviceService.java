package com.example.mqttdemo.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.mqttdemo.entity.Device;
import com.example.mqttdemo.exception.MqttApiException;
import com.example.mqttdemo.repository.DeviceRepository;

@Service
public class DeviceService {
	
	private static Logger log = LoggerFactory.getLogger(DeviceService.class);
	
	private static final String ERR_NOT_DEVICE = "Not device: %s";
	private static final String MSG_LOG_READ_DEVICE = "Device: %s read value %s";
	private static final String MSG_LOG_SAVE_DEVICE = "Device: %s save value %s";
	
	@Value("${mqtt.topicPub}")
	private String topic_pub;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private MqttPublisher mqttPublisher;

	public void publish(String deviceName, String value) throws MqttApiException {
		Optional<Device> device = readDevice(deviceName);
		if (device.isPresent()) {
			mqttPublisher.publishMessage(topic_pub + deviceName, value);
		//	mqtGateway.sendToMqtt(value, topic_pub + "/" + deviceName);
			device.get().setValue(value);
			deviceRepository.save(device.get());
			log.info(String.format(MSG_LOG_SAVE_DEVICE, device.get().getDevice() , device.get().getValue()));
		} else {
			throw new MqttApiException(String.format(ERR_NOT_DEVICE, deviceName));
		}
	}

	public void save(String deviceName, String value) throws MqttApiException {
		Optional<Device> device = readDevice(deviceName);
		if (device.isPresent()) {
			device.get().setValue(value);
			deviceRepository.save(device.get());
			log.info(String.format(MSG_LOG_SAVE_DEVICE, device.get().getDevice() , device.get().getValue()));
		} else {
			throw new MqttApiException(String.format(ERR_NOT_DEVICE, deviceName));
		}
	}

	public Device read(String deviceName) throws MqttApiException {
		Optional<Device> device = readDevice(deviceName);
		if (device.isPresent()) {
			log.info(String.format(MSG_LOG_READ_DEVICE, device.get().getDevice() , device.get().getValue()));
			return device.get();
		} else {
			throw new MqttApiException(String.format(ERR_NOT_DEVICE, deviceName));
		}
	}

	private Optional<Device> readDevice(String deviceName) {
		Optional<Device> device = Optional.ofNullable(deviceRepository.findByDevice(deviceName));
		return device;
	}
}
