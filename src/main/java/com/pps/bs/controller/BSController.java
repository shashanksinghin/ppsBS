package com.pps.bs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pps.bs.producer.JmsFCSProducer;
import com.pps.bs.producer.JmsPPSroducer;
import com.pps.bs.service.BSService;
import com.pps.common.enums.PaymentStatus;
import com.pps.common.model.PaymentCanonical;

import lombok.extern.slf4j.Slf4j;

/**
 * A rest controller to facilitate the unit testing.
 * @author shashank
 *
 */
@RestController
@Slf4j
public class BSController {
	
	@Autowired
	private JmsPPSroducer jmsBSProducer;
	
	@Autowired
	private JmsFCSProducer jmsFCSProducer;
	
	@Autowired
	private BSService bsService;
	
	@PostMapping("/sendPaymentBS")
	public void sendToTopic(@RequestBody String paymentCanonical) {
		jmsBSProducer.sendMessageTest(paymentCanonical);
	}
	
	@PostMapping("/sendPaymentFCS")
	public ResponseEntity<String> sendToPaymentForFCS(@RequestBody String paymentCanonicalJson) {
		log.info("Received Message to BS->: " + paymentCanonicalJson.toString());
		
		PaymentCanonical paymentCanonical = bsService.covertJsonToPojo(paymentCanonicalJson);

		bsService.addAuditEntry(paymentCanonical, PaymentStatus.SUCESSFUL, "BS001", "BS Input received. sent to FCS", "BS_Input");
		String paymentCanonicalXml = bsService.covertPojoToXml(paymentCanonical);
		
		jmsFCSProducer.sendMessage(paymentCanonicalXml);
		
		return ResponseEntity.ok(paymentCanonicalXml);
	}	
}
