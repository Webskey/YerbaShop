package org.yerbashop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.GetAllDao;
import org.yerbashop.model.Products;

/**
 * <h1>ProductsService class.</h1>
 *
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */

@Service
public class ProductsService {

	@Autowired
	private GetAllDao<Products> productsDao;

	@Transactional(readOnly = true)
	public List<Products> getProductList(){
		productsDao.setClazz("Products");
		return productsDao.getAll();
	}
}
