package org.yerbashop.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.HibernateUtil;
import org.yerbashop.model.Products;

@RunWith(MockitoJUnitRunner.class)
public class ProductsDaoTest {

	@Mock
	private SessionFactory sessionFactory;

	@InjectMocks
	protected ProductsDao productsDao;

	private static HibernateUtil hibernateUtil= new HibernateUtil();

	private static Session session;

	private List<Products> products;

	@BeforeClass
	public static void beforeClass() {
		session=hibernateUtil.getSessionFactory().openSession();
	}

	@Before
	public void setUp() {
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		products = productsDao.getAllProducts();
	}

	@AfterClass
	public static void  afterClass() {
		hibernateUtil.shutdown();
	}

	@Test
	public void shouldReturnProductsList_whenConnectedToDatabase() {
		assertEquals(products.size(),12);
		assertEquals(products.get(0).getName(),"Pajarito Seleccion Especial");
		assertEquals(products.get(1).getCategory(),"classicYerba");
		assertEquals(products.get(2).getPrice(),25);
		assertEquals(products.get(3).getDescription(),"The yerba mate Amanda flavoured with orange combines the mildness of the traditional Amanda, with orange essences, which brings a refreshing and new aroma.");
	}
}
