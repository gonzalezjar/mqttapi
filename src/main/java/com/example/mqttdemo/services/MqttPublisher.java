package com.example.mqttdemo.services;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.mqttdemo.exception.MqttApiException;


public class MqttPublisher {

	private static final Logger logger = LoggerFactory.getLogger(MqttPublisher.class);

	@Value("${mqtt.qos}")
	private int qos;

	@Autowired
	private MqttClient mqttClient;

	public void publishMessage(String topic, String message) throws MqttApiException {
		try {
			MqttMessage mqttmessage = new MqttMessage(message.getBytes());
			mqttmessage.setQos(qos);
			mqttmessage.setRetained(false);
			this.mqttClient.publish(topic, mqttmessage);
			logger.info(String.format("Publish in topic: %s and message: %s", topic, message));
		} catch (MqttException me) {
			throw new MqttApiException("Not connetion");
		}
	}
}