package com.osys.wallet.response;

public class ResponseDecode {

	private String type;
	private String currency;
	private Long created_at;
	private Long expiry;
	private String payee;
	private Long amount_msat;
	private String description;
	private Long min_final_cltv_expiry;
	private String payment_secret;
	private String features;
	private String payment_hash;
	private String signature;
	private Boolean valid;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Long created_at) {
		this.created_at = created_at;
	}
	public Long getExpiry() {
		return expiry;
	}
	public void setExpiry(Long expiry) {
		this.expiry = expiry;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public Long getAmount_msat() {
		return amount_msat;
	}
	public void setAmount_msat(Long amount_msat) {
		this.amount_msat = amount_msat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getMin_final_cltv_expiry() {
		return min_final_cltv_expiry;
	}
	public void setMin_final_cltv_expiry(Long min_final_cltv_expiry) {
		this.min_final_cltv_expiry = min_final_cltv_expiry;
	}
	public String getPayment_secret() {
		return payment_secret;
	}
	public void setPayment_secret(String payment_secret) {
		this.payment_secret = payment_secret;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public String getPayment_hash() {
		return payment_hash;
	}
	public void setPayment_hash(String payment_hash) {
		this.payment_hash = payment_hash;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}			
	


}
