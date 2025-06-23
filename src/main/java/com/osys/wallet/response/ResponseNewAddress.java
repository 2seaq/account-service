package com.osys.wallet.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ResponseNewAddress {

    @JsonProperty("bech32")
	private String address;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
