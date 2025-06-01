package com.pps.bs.service;

import com.pps.common.enums.PaymentStatus;
import com.pps.common.model.PaymentCanonical;

/**
 * 
 * @author shashank
 *
 */
public interface BSService {
	public String covertPojoToXml(PaymentCanonical paymentCanonical);
	
	public PaymentCanonical covertXmlToPojo(String paymentCanonicalXml);
	
	public String covertPojToJson(PaymentCanonical paymentCanonical);
	
	public PaymentCanonical covertJsonToPojo(String paymentCanonicalJson);
	
	public void addAuditEntry(PaymentCanonical paymentCanonical,PaymentStatus paymentStatus, String statusCode, String statusDesc, String operation);
	
}
