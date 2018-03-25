package org.yerbashop.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ORDERS")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orders_ID")
	private int id;

	@ManyToOne
	@JoinColumn(name = "USERNAME")
	private Users users;

	@Column(name="order_date", nullable = false, updatable=false, insertable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate = new Date();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "orders_products", 
	joinColumns = { @JoinColumn(name = "orders_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "products_id") })
	private Set<Products> productsList = new HashSet<>();

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Users getUsers() {
		return users;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setProductsList(Set<Products> productsList){
		this.productsList = productsList;
	}

	public Set<Products> getProductsList(){
		return productsList;
	}
}
