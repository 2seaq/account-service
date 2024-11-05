package com.osys.wallet.response;

import java.util.List;

public class ResponseInvoices {
	
	List<ResponseInvoicesInvoice> invoices;

	public List<ResponseInvoicesInvoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<ResponseInvoicesInvoice> invoices) {
		this.invoices = invoices;
	}

}

