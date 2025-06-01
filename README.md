The Broker System (BS)

1. it recives a fraud check request for a payment (in JSON) from the PPS and converts to XML.
2. sends the payment (in XML) to the FCS for executing a fraud check
3. receives the fraud check result (in XML) from FCS
4. converts the fraud check result to JSON and sends it to the PPS. 
