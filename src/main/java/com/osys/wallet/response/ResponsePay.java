package com.osys.wallet.response;

public class ResponsePay {

	private String destination;
	private String payment_hash;
	private Long created_at;
	private Long parts;	
	private Long amount_msat;
	private Long amount_sent_msat;
	private String payment_preimage;
	private String status;
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getPayment_hash() {
		return payment_hash;
	}
	public void setPayment_hash(String payment_hash) {
		this.payment_hash = payment_hash;
	}
	public Long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Long created_at) {
		this.created_at = created_at;
	}
	public Long getParts() {
		return parts;
	}
	public void setParts(Long parts) {
		this.parts = parts;
	}
	public Long getAmount_msat() {
		return amount_msat;
	}
	public void setAmount_msat(Long amount_msat) {
		this.amount_msat = amount_msat;
	}
	public Long getAmount_sent_msat() {
		return amount_sent_msat;
	}
	public void setAmount_sent_msat(Long amount_sent_msat) {
		this.amount_sent_msat = amount_sent_msat;
	}
	public String getPayment_preimage() {
		return payment_preimage;
	}
	public void setPayment_preimage(String payment_preimage) {
		this.payment_preimage = payment_preimage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
