// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.model;

public class Account {
	
	private String address;
	private Long paysTotal;
	private Long invoicesTotal;
	private Long depositTotal;
	private Long availableBalance;
	
	public Account(String address, Long paysTotal, Long invoicesTotal, Long depositTotal) {
		this.address = address;
		this.paysTotal = paysTotal;
		this.invoicesTotal = invoicesTotal;
		this.depositTotal = depositTotal;
		this.availableBalance = (this.depositTotal + this.invoicesTotal) - this.paysTotal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPaysTotal() {
		return paysTotal;
	}

	public void setPaysTotal(Long paysTotal) {
		this.paysTotal = paysTotal;
	}

	public Long getInvoicesTotal() {
		return invoicesTotal;
	}

	public void setInvoicesTotal(Long invoicesTotal) {
		this.invoicesTotal = invoicesTotal;
	}

	public Long getDepositTotal() {
		return depositTotal;
	}

	public void setDepositTotal(Long depositTotal) {
		this.depositTotal = depositTotal;
	}

	public Long getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Long availableBalance) {
		this.availableBalance = availableBalance;
	}
	


}
