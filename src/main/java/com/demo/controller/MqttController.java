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
@RequestMapping("/api/cmd")
public class MqttController {

	@Autowired
	DeviceService deviceService;

	@PostMapping()
	public ResponseEntity<?> publish(@RequestBody DeviceJson deviceJson) {
		try {
			deviceService.publishAndSave(deviceJson.getDevice(), deviceJson.getValue());
			return ResponseEntity.ok("Success");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

	@GetMapping()
	public ResponseEntity<?> read(@RequestParam("device") String deviceName) {
		try {
			Device device = deviceService.read(deviceName);
			return ResponseEntity.ok(mapperJson(device));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

	private DeviceJson mapperJson(Device device) {
		DeviceJson json = new DeviceJson();
		json.setId(device.getId());
		json.setDevice(device.getDevice());
		json.setValue(device.getValue());
		return json;
	}

}
