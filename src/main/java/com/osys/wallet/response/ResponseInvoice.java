package com.osys.wallet.response;

public class ResponseInvoice {

	private String payment_hash;
	private Long expires_at;
	private String bolt11;
	private String payment_secret;
	private String warning_capacity;
	public String getPayment_hash() {
		return payment_hash;
	}
	public void setPayment_hash(String payment_hash) {
		this.payment_hash = payment_hash;
	}
	public Long getExpires_at() {
		return expires_at;
	}
	public void setExpires_at(Long expires_at) {
		this.expires_at = expires_at;
	}
	public String getBolt11() {
		return bolt11;
	}
	public void setBolt11(String bolt11) {
		this.bolt11 = bolt11;
	}
	public String getPayment_secret() {
		return payment_secret;
	}
	public void setPayment_secret(String payment_secret) {
		this.payment_secret = payment_secret;
	}
	public String getWarning_capacity() {
		return warning_capacity;
	}
	public void setWarning_capacity(String warning_capacity) {
		this.warning_capacity = warning_capacity;
	}
	
}
