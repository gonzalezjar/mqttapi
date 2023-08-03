package com.example.mqttdemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mqttdemo.entity.Device;
import com.example.mqttdemo.exception.MqttApiException;
import com.example.mqttdemo.json.DeviceJson;
import com.example.mqttdemo.json.ResponseJson;
import com.example.mqttdemo.services.DeviceService;


@RestController
@RequestMapping("/api/cmd")
public class MqttController {
	
	private static Logger log = LoggerFactory.getLogger(MqttController.class);
	
	@Autowired
	private DeviceService deviceService;

	@PostMapping()
	public ResponseEntity<?> cmd(@RequestBody DeviceJson deviceJson) {
		try {
			deviceService.publish(deviceJson.getDevice(), deviceJson.getValue());
			return ResponseEntity.ok(new ResponseJson("Success"));
		} catch (MqttApiException ex) {
			log.info(ex.getMessage());
			return ResponseEntity.ok(new ResponseJson(ex.getMessage()));
		}
	}

	@GetMapping()
	public ResponseEntity<?> cmd(@RequestParam("device") String deviceName) {
		try {
			Device device = deviceService.read(deviceName);
			return ResponseEntity.ok(mapperDeviceJson(device));
		} catch (MqttApiException ex) {
			log.info(ex.getMessage());
			return ResponseEntity.ok(new ResponseJson(ex.getMessage()));
		}
	}

	private DeviceJson mapperDeviceJson(Device device) {
		DeviceJson json = new DeviceJson();
		json.setId(device.getId());
		json.setDevice(device.getDevice());
		json.setValue(device.getValue());
		return json;
	}
	
}