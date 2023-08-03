package com.example.mqttdemo.services;

import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MqttSubscriber implements MqttCallback {

	private static final Logger logger = LoggerFactory.getLogger(MqttSubscriber.class);

	@Autowired
	private DeviceService deviceService;

	@Override
	public void connectionLost(Throwable cause) {
		logger.info("Connection Lost" + cause);
	}

	@Override
	public void messageArrived(String mqttTopic, MqttMessage mqttMessage) throws Exception {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		logger.info(String.format("Message Arrived at Time: %s  Topic: %s  Message: %s", time, mqttTopic,
				new String(mqttMessage.getPayload())));

		if (mqttTopic.equals("esp8266/sensor/temperature")) {
			deviceService.save("sensor_1", new String(mqttMessage.getPayload()));
		} else if (mqttTopic.equals("esp8266/sensor/humidity")) {
			deviceService.save("sensor_2", new String(mqttMessage.getPayload()));
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

	}
}