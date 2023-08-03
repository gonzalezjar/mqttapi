package com.example.mqttdemo.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceJson {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("device")
	private String device;
	
	@JsonProperty("value")
	private String value;

}