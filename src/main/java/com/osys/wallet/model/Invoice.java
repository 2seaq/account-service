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
public class Invoice {

	private @Id @GeneratedValue Long id;
	private String label;
	private Long amountMsat;
	private String description;
	private String status;
	private Long timestamp;
	
	@Column(name = "bolt11", length = 1024)
	private String bolt11;

	private String payment_hash;
	
	private @Version @JsonIgnore Long version;

	private @ManyToOne Manager manager;

	private Invoice() {}

	public Invoice(String label, String bolt11, Long amountMsat, String description, Long timestamp, String status, Manager manager) {
		this.label = label;
		this.bolt11 = bolt11;
		this.amountMsat = amountMsat;
		this.description = description;
		this.manager = manager;
		this.timestamp = timestamp;
		this.status = status;		
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Invoice invoice = (Invoice) o;
		return Objects.equals(id, invoice.id) &&
			Objects.equals(label, invoice.label) &&
			Objects.equals(amountMsat, invoice.amountMsat) &&
			Objects.equals(description, invoice.description) &&
			Objects.equals(version, invoice.version) &&
			Objects.equals(manager, invoice.manager);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, label, amountMsat, description, version, manager);
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
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
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getAmountMsat() {
		return amountMsat;
	}

	public void setAmountMsat(Long amountMsat) {
		this.amountMsat = amountMsat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "Invoice{" +
			"id=" + id +
			", label='" + label + '\'' +
			", amountMsat='" + amountMsat + '\'' +
			", description='" + description + '\'' +
			", bolt11='" + bolt11 + '\'' +
			", payment_hash='" + payment_hash + '\'' +						
			", version=" + version +
			", manager=" + manager +
			'}';
	}

	public String getPayment_hash() {
		return payment_hash;
	}

	public void setPayment_hash(String payment_hash) {
		this.payment_hash = payment_hash;
	}
}
