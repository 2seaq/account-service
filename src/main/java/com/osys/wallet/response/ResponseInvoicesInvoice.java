package com.osys.wallet.response;

public class ResponseInvoicesInvoice {

	private String label;
	private String bolt11;
	private String payment_hash;
	private Long msatoshi;
	private String amount_msat;
	private String status;
	private String decription;
	private Long expires_at;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getBolt11() {
		return bolt11;
	}
	public void setBolt11(String bolt11) {
		this.bolt11 = bolt11;
	}
	public String getPayment_hash() {
		return payment_hash;
	}
	public void setPayment_hash(String payment_hash) {
		this.payment_hash = payment_hash;
	}
	public Long getMsatoshi() {
		return msatoshi;
	}
	public void setMsatoshi(Long msatoshi) {
		this.msatoshi = msatoshi;
	}
	public String getAmount_msat() {
		return amount_msat;
	}
	public void setAmount_msat(String amount_msat) {
		this.amount_msat = amount_msat;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDecription() {
		return decription;
	}
	public void setDecription(String decription) {
		this.decription = decription;
	}
	public Long getExpires_at() {
		return expires_at;
	}
	public void setExpires_at(Long expires_at) {
		this.expires_at = expires_at;
	}
	
}
