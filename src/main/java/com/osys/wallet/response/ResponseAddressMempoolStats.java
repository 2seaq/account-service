package com.osys.wallet.response;

public class ResponseAddressMempoolStats {

	private Integer funded_txo_count;
	private Long funded_txo_sum;
	private Integer spent_txo_count;
	private Long spent_txo_sum;
	private Integer tx_count;

	public Integer getFunded_txo_count() {
		return funded_txo_count;
	}
	public void setFunded_txo_count(Integer funded_txo_count) {
		this.funded_txo_count = funded_txo_count;
	}
	public Long getFunded_txo_sum() {
		return funded_txo_sum;
	}
	public void setFunded_txo_sum(Long funded_txo_sum) {
		this.funded_txo_sum = funded_txo_sum;
	}
	public Integer getSpent_txo_count() {
		return spent_txo_count;
	}
	public void setSpent_txo_count(Integer spent_txo_count) {
		this.spent_txo_count = spent_txo_count;
	}
	public Long getSpent_txo_sum() {
		return spent_txo_sum;
	}
	public void setSpent_txo_sum(Long spent_txo_sum) {
		this.spent_txo_sum = spent_txo_sum;
	}
	public Integer getTx_count() {
		return tx_count;
	}
	public void setTx_count(Integer tx_count) {
		this.tx_count = tx_count;
	}

}
