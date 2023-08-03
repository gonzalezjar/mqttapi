package com.example.mqttdemo.config;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.mqttdemo.services.MqttPublisher;
import com.example.mqttdemo.services.MqttSubscriber;

@Configuration
public class MqttConfig {

	@Value("${mqtt.topicSub}")
	private String topicSub;

	@Value("${mqtt.brokerUrl}")
	private String brokerUrl;

	@Value("${mqtt.qos}")
	private int qos;

	@Value("${mqtt.userName}")
	private String userName;

	@Value("${mqtt.password}")
	private String password;

	private final String clientId = UUID.randomUUID().toString();

	@Bean
	public MqttClient mqttClient() throws MqttException {
		MemoryPersistence persistence = new MemoryPersistence();
		MqttConnectOptions connectionOptions = new MqttConnectOptions();
		MqttClient mqttClient = new MqttClient(this.brokerUrl, this.clientId, persistence);
		connectionOptions.setCleanSession(true);
		connectionOptions.setPassword(this.password.toCharArray());
		connectionOptions.setUserName(this.userName);
		mqttClient.connect(connectionOptions);
		return mqttClient;
	}

	@Bean
	public MqttSubscriber mqttSubscriber(MqttClient mqttClient) throws MqttException {
		MqttSubscriber mqttSubscriber = new MqttSubscriber();
		mqttClient.setCallback(mqttSubscriber);
		mqttClient.subscribe(this.topicSub, this.qos);
		return mqttSubscriber;
	}

	@Bean
	public MqttPublisher mqttPublisher() {
		return new MqttPublisher();
	}

}