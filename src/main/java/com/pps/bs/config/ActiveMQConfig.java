package com.pps.bs.config;

import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import io.micrometer.observation.ObservationRegistry;
import jakarta.jms.ConnectionFactory;

/**
 * This is a configuration class to setup Active MQ
 * 
 * @author shashank
 *
 */
@Configuration
public class ActiveMQConfig {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;

	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(brokerUrl);
		activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.pps.bs", "com.pps.common.model"));
		return activeMQConnectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, ObservationRegistry observationRegistry) {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setObservationRegistry(observationRegistry);
//		jmsTemplate.setPubSubDomain(true); // enable for Pub Sub to topic. Not Required for Queue.
		return jmsTemplate;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory, ObservationRegistry observationRegistry) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setObservationRegistry(observationRegistry);
//		factory.setPubSubDomain(true);
		return factory;
	}
	
//	@Bean
//	public DefaultMessageListenerContainer  defaultMessageListenerContainer (ConnectionFactory connectionFactory, ObservationRegistry observationRegistry) {
//		DefaultMessageListenerContainer factory = new DefaultMessageListenerContainer();
//		factory.setConnectionFactory(connectionFactory);
//		factory.setObservationRegistry(observationRegistry);
//		factory.setDestinationName("${active-mq.bs-req-topic}");
//		return factory;
//	}
}