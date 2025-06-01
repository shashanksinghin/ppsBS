package com.pps.bs.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.pps.bs.service.BSService;
import com.pps.common.enums.PaymentStatus;
import com.pps.common.model.PaymentAudit;
import com.pps.common.model.PaymentCanonical;
import com.pps.common.utils.MessageConvertor;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author shashank
 *
 */
@Service
@Slf4j
public class BSServiceImpl implements BSService {

	@Override
	public String covertPojoToXml(PaymentCanonical paymentCanonical) {
		return MessageConvertor.covertPojoToXml(paymentCanonical);
	}

	@Override
	public PaymentCanonical covertXmlToPojo(String paymentCanonicalXml) {
		return MessageConvertor.covertXmlToPojo(paymentCanonicalXml);
	}

	@Override
	public String covertPojToJson(PaymentCanonical paymentCanonical) {
		return MessageConvertor.covertPojToJson(paymentCanonical);
	}

	@Override
	public PaymentCanonical covertJsonToPojo(String paymentCanonicalJson) {
		return MessageConvertor.covertJsonToPaymentCanonicalPojo(paymentCanonicalJson);
	}

	@Override
	public void addAuditEntry(PaymentCanonical paymentCanonical, PaymentStatus paymentStatus, String statusCode,
			String statusDesc, String operation) {
		if (paymentCanonical.getPaymentAudits() == null) {
			paymentCanonical.setPaymentAudits(new ArrayList<>());
		}

		PaymentAudit paymentAudit = new PaymentAudit();
		paymentCanonical.getPaymentAudits().add(paymentAudit);

		paymentAudit.setPaymentStatus(paymentStatus);
		paymentAudit.setStatusCode(statusCode);
		paymentAudit.setStatusDesc(statusDesc);
		paymentAudit.setStatusDateTime(LocalDateTime.now());
		paymentAudit.setPaymentOperation(operation);
		
		log.debug("Generated Audit entry -{{}}", paymentAudit);
		
		return;
	}

}
