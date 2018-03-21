package org.yerbashop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.ProductsDao;
import org.yerbashop.model.Products;

@Service
public class ProductsService {

	@Autowired
	private ProductsDao productsDao;
	
	@Transactional(readOnly = true)
	public List<Products> getProductList(){
		return productsDao.getAllProducts();
	}
}
