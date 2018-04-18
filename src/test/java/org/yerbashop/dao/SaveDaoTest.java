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
import org.yerbashop.dummybuilders.UsersModelBuilder;
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

	private Users user;

	@BeforeClass
	public static void beforeClass() {
		session = hibernateUtil.getSessionFactory().openSession();
	}

	@Before
	public void setUp() {
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		transaction = session.getTransaction();
		transaction.begin();

		UsersModelBuilder usersBuilder = new UsersModelBuilder(Users.class);
		user = (Users) usersBuilder.getObject();
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
		user.setUsername("foo");
		saveDao.save(user);
		transaction.commit();
		Users duzer = session.load(Users.class, user.getUsername());
		assertEquals(user.getFirstname(),duzer.getFirstname());

		after(user);
	}

	@Test(expected=javax.persistence.PersistenceException.class)
	public void shouldThrowConstraitViolationException_whenUsernameAlreadyExists() {
		saveDao.save(user);
		transaction.commit();
	}
}
