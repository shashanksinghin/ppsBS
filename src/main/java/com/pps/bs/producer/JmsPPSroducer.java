package com.pps.bs.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * A JMS producer to send processed payment to PPS.
 * 
 * @author shashank
 *
 */
@Component
@Slf4j
public class JmsPPSroducer {

	@Autowired
	JmsTemplate jmsTemplate;

	@Value("${active-mq.bs-res-topic}")
	private String resTopic;

	@Value("${active-mq.bs-req-topic}")
	private String reqBSTopic;

	public void sendMessage(String jsonMessage) {
		try {
			log.info("Attempting Send message to PPS on Topic: " + resTopic);
			jmsTemplate.convertAndSend(resTopic, jsonMessage);
		} catch (Exception e) {
			log.error("Recieved Exception during send Message: ", e);
		}
	}

	public void sendMessageTest(String message) {
		try {
			log.info("Attempting Send message to BS on Topic: " + reqBSTopic);
			jmsTemplate.convertAndSend(reqBSTopic, message);
		} catch (Exception e) {
			log.error("Recieved Exception during send Message: ", e);
		}
	}
}