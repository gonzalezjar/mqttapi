package com.demo.services;

import java.util.Optional;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.MqttGateway;
import com.demo.entity.Device;
import com.demo.exeption.MqttApiException;
import com.demo.repository.DeviceRepository;

@Service
public class DeviceService {
	
	private static Logger log = LoggerFactory.getLogger(DeviceService.class);
	
	private static final String ERR_NOT_DEVICE = "Not device: %s";
	private static final String MSG_LOG_READ_DEVICE = "Device: %s read value %s";
	private static final String MSG_LOG_SAVE_DEVICE = "Device: %s save value %s";
	
	@Value("${mqtt.topic.pub}")
	private String topic_pub;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private MqttGateway mqtGateway;

	public void publish(String deviceName, String value) throws MqttApiException {
		Optional<Device> device = readDevice(deviceName);
		if (device.isPresent()) {
			mqtGateway.sendToMqtt(value, topic_pub + "/" + deviceName);
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
