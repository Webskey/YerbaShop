package org.yerbashop.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "USERS")
public class Users {
	
	@Id
	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "ENABLED",columnDefinition = "BIT", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;
	
	@Column(name = "phone_number")
	private String phoneNr;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "adress")
	private String adress;
	
	@Column(name = "email")
	private String email;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
	private Set<UserRoles> userRoles = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "users")
	private Set<Orders> orders = new HashSet<>();
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}
	
	public Set<UserRoles> getUserRoles() {
		return userRoles;
	}
	
	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}
	
	public Set<Orders> getOrders() {
		return orders;
	}
	
	public void setPhoneNr(String phoneNr) {
		this.phoneNr=phoneNr;
	}
	
	public String getPhoneNr() {
		return phoneNr;
	}
	
	public String getAdress() {
		return adress;
	}
	
	public void setAdress(String adress) {
		this.adress=adress;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}