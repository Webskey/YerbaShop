package org.yerbashop.dummybuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.yerbashop.model.Products;

public class ProductsBuilder {
	
	private List<Products> products;
	
	public ProductsBuilder() {
		products = new ArrayList<Products>();

		Products p1 = new Products();
		Products p2 = new Products();
		Products p3 = new Products();

		p1.setName("Yerba Mate");
		p1.setCategory("classicYerba");
		p1.setPrice(20);

		p2.setName("Green Mate");
		p2.setCategory("flavouredYerba");
		p2.setPrice(10);

		p3.setName("Metal Gourd");
		p3.setCategory("gourdsAccesories");
		p3.setPrice(15);

		products.add(p1);
		products.add(p2);
		products.add(p3);
	}
	
	public List<Products> getProductsList(){
		return products;
	}
	
	public Set<Products> getProductsSet(){
		Set<Products> productsSet = new HashSet<>();
		products.addAll(products);
		return productsSet;
	}
}
