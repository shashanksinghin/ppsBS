package com.pps.bs.consumer;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.pps.bs.producer.JmsFCSProducer;
import com.pps.bs.service.BSService;
import com.pps.common.enums.PaymentStatus;
import com.pps.common.model.PaymentCanonical;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lombok.extern.slf4j.Slf4j;

/**
 * A JMS Consumer to receive payment for BS processing.
 * 
 * @author shashank
 *
 */
@Component
@Slf4j
public class JmsBSConsumer implements MessageListener {

	@Autowired
	private JmsFCSProducer jmsFCSProducer;
	
	@Autowired
	private BSService bsService;

	@Override
	@JmsListener(destination = "${active-mq.bs-req-topic}")
	public void onMessage(Message message) {
		try {
			log.info("Received Message to BS->: " + message.toString());
			
			ActiveMQTextMessage objectMessage = (ActiveMQTextMessage) message;
			String paymentCanonicalStr = objectMessage.getText();
			
			PaymentCanonical paymentCanonical = bsService.covertJsonToPojo(paymentCanonicalStr);

			bsService.addAuditEntry(paymentCanonical, PaymentStatus.SUCESSFUL, "BS001", "BS Input received.", "BS_Input");
			String paymentCanonicalXml = bsService.covertPojoToXml(paymentCanonical);
			
			jmsFCSProducer.sendMessage(paymentCanonicalXml);
		} catch (Exception e) {
			log.error("Received Exception : " + e);
		}
	}
}