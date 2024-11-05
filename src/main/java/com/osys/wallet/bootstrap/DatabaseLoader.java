// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.osys.wallet.repository.BitcoinAddressRepository;
import com.osys.wallet.repository.InvoiceRepository;
import com.osys.wallet.repository.ManagerRepository;
import com.osys.wallet.repository.PayRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final ManagerRepository managers;
	private final InvoiceRepository invoices;
	private final PayRepository pays;
	private final BitcoinAddressRepository bitcoinaddresses;

	@Autowired
	public DatabaseLoader(InvoiceRepository invoiceRepository, 
	PayRepository payRepository, 
	ManagerRepository managerRepository, 
	BitcoinAddressRepository bitcoinaddressRepository) {

		this.managers = managerRepository;
		this.invoices = invoiceRepository;
		this.pays = payRepository;
		this.bitcoinaddresses =	bitcoinaddressRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
	
		System.out.println("DatabaseLoader run");
		SecurityContextHolder.clearContext();
	}
}
