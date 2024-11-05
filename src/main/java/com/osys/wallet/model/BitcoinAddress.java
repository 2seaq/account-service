// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class BitcoinAddress {

	private @Id @GeneratedValue Long id;
	@Column(name="btcaddress", columnDefinition="TEXT") 
	private String btcaddress;
	
	private @Version @JsonIgnore Long version;

	private @ManyToOne Manager manager;

	private BitcoinAddress() {}

	public BitcoinAddress(String btcaddress, Manager manager) {
		this.btcaddress = btcaddress;
		this.manager = manager;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BitcoinAddress bitcoinaddress = (BitcoinAddress) o;
		return Objects.equals(id, bitcoinaddress.id) &&
			Objects.equals(btcaddress, bitcoinaddress.btcaddress) &&
			Objects.equals(version, bitcoinaddress.version) &&
			Objects.equals(manager, bitcoinaddress.manager);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, btcaddress, version, manager);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBtcAddress() {
		return btcaddress;
	}

	public void setBtcAddress(String btcaddress) {
		this.btcaddress = btcaddress;
	}
		
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "Deposit{" +
			"id=" + id +
			", btcaddress='" + btcaddress + '\'' +
			", version=" + version +
			", manager=" + manager +
			'}';
	}
}