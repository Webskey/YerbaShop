package org.yerbashop.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.HibernateUtil;
import org.yerbashop.model.Users;

@RunWith(MockitoJUnitRunner.class)
public class SaveDaoTest {

	@Mock
	private SessionFactory sessionFactory;

	@InjectMocks
	private SaveDao saveDao;

	private static Session session;

	private static HibernateUtil hibernateUtil= new HibernateUtil();

	private Transaction transaction;

	@BeforeClass
	public static void beforeClass() {
		session = hibernateUtil.getSessionFactory().openSession();
	}

	@Before
	public void setUp() {
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		transaction = session.getTransaction();
		transaction.begin();
	}

	public void  after(Object object) {
		transaction = session.getTransaction();
		transaction.begin();
		session.clear();
		session.remove(object);
		transaction.commit();
	}

	@AfterClass
	public static void  afterClass() {
		hibernateUtil.shutdown();
	}

	@Test
	public void shouldSaveProperly_whenDetailsProvidedCorrectly() {
		saveDao.save(user());
		transaction.commit();
		Users duzer = session.load(Users.class, user().getUsername());
		assertEquals(user().getFirstname(),duzer.getFirstname());

		after(user());
	}

	@Test(expected=javax.persistence.PersistenceException.class)
	public void shouldThrowConstraitViolationException_whenUsernameAlreadyExists() {
		Users existing =user();
		existing.setUsername("username");

		saveDao.save(existing);
		transaction.commit();
	}

	private Users user(){
		Users user = new Users();
		user.setUsername("usertest");
		user.setPassword("password");
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setAdress("adress");
		user.setEmail("email@email.com");
		user.setPhoneNr("phoneNr");
		user.setEnabled(true);
		return user;
	}
}
