package org.yerbashop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
public class UserRoles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ROLE_ID")
	private int id;
	
	@Column(name = "ROLE")
	private String role;

	@ManyToOne
	@JoinColumn(name = "USERNAME")
	private Users users;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public Users getUsers() {
		return users;
	}
}