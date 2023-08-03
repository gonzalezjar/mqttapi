package com.example.mqttdemo.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@Getter
@Setter
public class ResponseJson {
	
	@JsonProperty("message")
	private String message;
	
	public ResponseJson(String message) {
		this.message = message;
	}
}