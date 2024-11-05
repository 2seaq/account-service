// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.model;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pay {

	private @Id @GeneratedValue Long id;
	@Column(name="bolt11", columnDefinition="TEXT") 
	private String bolt11;
	private Long amountMsat;
	private Long timestamp;
	private String status;
	private String bolt11Description;
	private String bolt11Payee;

	private @Version @JsonIgnore Long version;
	private @ManyToOne Manager manager;

	private Pay() {}

	public Pay(String bolt11, String bolt11Description, Long amountMsat, Long timestamp, String status, Manager manager) {
		this.bolt11 = bolt11;
		this.amountMsat = amountMsat;
		this.timestamp = timestamp;
		this.status = status;
		this.manager = manager;
		this.bolt11Description = bolt11Description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pay pay = (Pay) o;
		return Objects.equals(id, pay.id) &&
			Objects.equals(bolt11, pay.bolt11) &&
			Objects.equals(amountMsat, pay.amountMsat) &&
			Objects.equals(timestamp, pay.timestamp) &&
			Objects.equals(status, pay.status) &&
			Objects.equals(version, pay.version) &&
			Objects.equals(manager, pay.manager);
	}

	public String getBolt11Description() {
		return bolt11Description;
	}

	public void setBolt11Description(String bolt11Description) {
		this.bolt11Description = bolt11Description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, bolt11, amountMsat, timestamp, status, version, manager);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBolt11() {
		return bolt11;
	}

	public void setBolt11(String bolt11) {
		this.bolt11 = bolt11;
	}

	public Long getAmountMsat() {
		return amountMsat;
	}

	public void setAmountMsat(Long amountMsat) {
		this.amountMsat = amountMsat;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "Pay{" +
			"id=" + id +
			", bolt11='" + bolt11 + '\'' +
			", bolt11Description='" + bolt11Description + '\'' +
			", amountMsat='" + amountMsat + '\'' +
			", timestamp='" + timestamp + '\'' +
			", status='" + status + '\'' +
			", version=" + version +
			", manager=" + manager +
			'}';
	}

	public String getBolt11Payee() {
		return bolt11Payee;
	}

	public void setBolt11Payee(String bolt11Payee) {
		this.bolt11Payee = bolt11Payee;
	}
}