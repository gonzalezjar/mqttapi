package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Device;
import com.demo.json.DeviceJson;
import com.demo.services.DeviceService;

@RestController
@RequestMapping("/api")
public class MqttController {

	@Autowired
	DeviceService deviceService;

	@PostMapping("/publish")
	public ResponseEntity<?> publish(@RequestBody DeviceJson deviceJson) {
		try {
			deviceService.publishAndSave(deviceJson.getName(), deviceJson.getValue());
			return ResponseEntity.ok("Success");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

	@GetMapping("/read")
	public ResponseEntity<?> read(@RequestParam String name) {
		try {
			Device device = deviceService.read(name);
			return ResponseEntity.ok(mapperJson(device));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

	private DeviceJson mapperJson(Device device) {
		DeviceJson json = new DeviceJson();
		json.setId(device.getId());
		json.setName(device.getName());
		json.setValue(device.getValue());
		return json;
	}

}
