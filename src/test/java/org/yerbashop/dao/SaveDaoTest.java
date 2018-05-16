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

	@AfterClass
	public static void  afterClass() {
		hibernateUtil.shutdown();
	}
	
	public void  after(Object object) {
		transaction = session.getTransaction();
		transaction.begin();
		session.clear();
		session.remove(object);
		transaction.commit();
	}

	@Test
	public void shouldSaveUser_whenCorrectData() {
		//given
		user.setUsername("foo");
		//when
		saveDao.save(user);
		transaction.commit();
		//then
		Users duzer = session.load(Users.class, user.getUsername());
		assertEquals(user, duzer);
		//finally
		after(user);
	}

	@Test(expected=javax.persistence.PersistenceException.class)
	public void shouldThrow_whenUsernameAlreadyExists() {
		//when
		saveDao.save(user);
		transaction.commit();
		//then PersistenceException thrown
	}
}
