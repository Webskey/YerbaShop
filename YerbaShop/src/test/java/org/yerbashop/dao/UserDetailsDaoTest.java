package org.yerbashop.dao;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Set;

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
import org.yerbashop.model.UserRoles;
import org.yerbashop.model.Users;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsDaoTest {

	@Mock
	private SessionFactory sessionFactory;

	@InjectMocks
	protected UserDetailsDa userDetailsDao;

	private static HibernateUtil hibernateUtil= new HibernateUtil();

	private static Session session;

	@BeforeClass
	public static void beforeClass() {
		session=hibernateUtil.getSessionFactory().openSession();
	}

	@Before
	public void setUp() {
		when(sessionFactory.getCurrentSession()).thenReturn(session);
	}

	@AfterClass
	public static void  afterClass() {
		hibernateUtil.shutdown();
	}

	@Test
	public void shouldReturnUserDetails_whenConnectedToDatabase() {
		Users user = userDetailsDao.findUserByUsername("username");
		assertEquals("username", user.getUsername());
		assertEquals("$2a$10$NL.J1pienoiQoF6NuR/30Otx0D.rT3yeQSs4rwt9stF/Dc6xgWnKy", user.getPassword());
		assertEquals("name", user.getFirstname());
		assertEquals("surname", user.getLastname());
		assertEquals("email@email.com", user.getEmail());
		assertEquals("Adress", user.getAdress());
		assertEquals("Phone Number", user.getPhoneNr());
		assertEquals(true, user.isEnabled());
	}

	@Test
	public void shouldUserBeNull_whenUserDoestExist() {
		Users user = userDetailsDao.findUserByUsername("fail");
		assertNull(user);
	}

	@Test(expected = NullPointerException.class)
	public void shouldAssertNull_whenFieldIsEmpty() {
		Users user = userDetailsDao.findUserByUsername("usernames");
		assertEquals("usernames", user.getUsername());
		assertNull(user.getAdress());
		assertEquals(true, user.isEnabled());
	}

	@Test
	public void shouldReturnROLEUSER_whenUserProvided(){
		Users user = userDetailsDao.findUserByUsername("username");
		Set<UserRoles> userRoles = user.getUserRoles();
		String[] userRole = userRoles.stream().map(s->s.getRole()).toArray(String[]::new);
		assertEquals(Arrays.toString(userRole),"[ROLE_USER]");
	}

	@Test
	public void shouldReturnROLEADMIN_whenAdminProvided(){
		Users user = userDetailsDao.findUserByUsername("admin");
		Set<UserRoles> userRoles = user.getUserRoles();
		String[] userRole = userRoles.stream().map(s->s.getRole()).toArray(String[]::new);
		assertThat(userRole, arrayContainingInAnyOrder("ROLE_USER","ROLE_ADMIN"));
	}
}
