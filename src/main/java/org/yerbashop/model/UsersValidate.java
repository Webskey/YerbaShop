package org.yerbashop.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UsersValidate {

	@NotEmpty(message="Username is required.")
	@Length(min=4,max=15, message= "Your username has to contain 4-15 characters.")
	private String username;

	@NotEmpty(message="Password is required.")
	@Length(min=5,max=10, message = "Your password has to contain 5-10 characters.")
	private String password;

	private boolean enabled;
	@Pattern(regexp="[0-9]{9}", message="Wrong phone number format.")
	private String phoneNr;

	private String firstname;

	private String lastname;

	private String adress;

	@NotEmpty(message="Email is required.")
	@Pattern(regexp=".+@.+\\..+", message="Wrong email fromat.")
	private String email;

	private Set<UserRoles> userRoles = new HashSet<>();

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