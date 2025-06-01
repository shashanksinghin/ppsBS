package com.pps.bs.consumer;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.pps.bs.producer.JmsPPSroducer;
import com.pps.bs.service.BSService;
import com.pps.common.enums.PaymentStatus;
import com.pps.common.model.PaymentCanonical;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lombok.extern.slf4j.Slf4j;

/**
 * A JMS Consumer to receive payment for FCS processing.
 * 
 * @author shashank
 *
 */
@Component
@Slf4j
public class JmsFCSConsumer implements MessageListener {

	@Autowired
	private BSService bsService;

	@Autowired
	private JmsPPSroducer jmsBSProducer;

	@Override
	@JmsListener(destination = "${active-mq.fcs-res-topic}")
	public void onMessage(Message message) {
		try {
			ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;

			String paymentCanonicalXml = activeMQTextMessage.getText();
			log.info("Received XML Message from FCS->" + paymentCanonicalXml);

			PaymentCanonical paymentCanonical = bsService.covertXmlToPojo(paymentCanonicalXml);
			bsService.addAuditEntry(paymentCanonical, PaymentStatus.SUCESSFUL, "BS002", "FCS response received.", "BS_Output");

			String paymentCanonicalJson = bsService.covertPojToJson(paymentCanonical);

			jmsBSProducer.sendMessage(paymentCanonicalJson);
		} catch (Exception e) {
			log.error("Received Exception : " + e);
		}
	}
}