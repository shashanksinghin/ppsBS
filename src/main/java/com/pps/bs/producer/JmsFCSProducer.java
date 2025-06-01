package com.pps.bs.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * A JMS producer to send processed payment to FCS.
 * 
 * @author shashank
 *
 */
@Component
@Slf4j
public class JmsFCSProducer {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${active-mq.fcs-req-topic}")
	private String reqTopic;

	public void sendMessage(String messageXml) {
		try {

			log.info("Attempting Send message to FCS on Topic: " + reqTopic);
			log.debug("request XML to FCS ->" + messageXml);

			jmsTemplate.convertAndSend(reqTopic, messageXml);
		} catch (Exception e) {
			log.error("Recieved Exception during send Message: ", e);
		}
	}
}