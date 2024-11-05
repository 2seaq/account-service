// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Manager {

	private @Id @GeneratedValue Long id;
	private String sub;
	private String name;
	private String email;

	public Manager() {}

	public Manager(String sub, String name, String email ) {
		this.sub = sub;
		this.email = email;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Manager manager = (Manager) o;
		return Objects.equals(id, manager.id) &&
			Objects.equals(name, manager.name) &&
			Objects.equals(email, manager.email);
	}

	@Override
	public int hashCode() {

		int result = Objects.hash(id, name, email);
		result = 31 * result;
		return result;
	}	

	@Override
	public String toString() {
		return "Manager{" +
			"id=" + id +
			", name='" + name + '\'' +
			", email=" + email +
			'}';
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}
}
