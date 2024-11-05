package com.osys.wallet.response;

public class ResponseAddress {

	private String address;
	private ResponseAddressChainStats chain_stats;
	private ResponseAddressMempoolStats mempool_stats;

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ResponseAddressChainStats getChain_stats() {
		return chain_stats;
	}
	public void setChain_stats(ResponseAddressChainStats chain_stats) {
		this.chain_stats = chain_stats;
	}
	public ResponseAddressMempoolStats getMempool_stats() {
		return mempool_stats;
	}
	public void setMempool_stats(ResponseAddressMempoolStats mempool_stats) {
		this.mempool_stats = mempool_stats;
	}

	
}
