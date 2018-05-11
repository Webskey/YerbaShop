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
public class LoadByIdDaoTest {

	@Mock
	private SessionFactory sessionFactory;

	@InjectMocks
	protected LoadByIdDao<Users> loadByIdDao;

	private static HibernateUtil hibernateUtil= new HibernateUtil();

	private static Session session;

	@BeforeClass
	public static void beforeClass() {
		session=hibernateUtil.getSessionFactory().openSession();
	}

	@Before
	public void setUp() {
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		loadByIdDao.setClazz("org.yerbashop.model.Users");
	}

	@AfterClass
	public static void  afterClass() {
		hibernateUtil.shutdown();
	}

	@Test
	public void shouldReturnUserDetails_whenConnectedToDatabase() {
		//when
		Users user = loadByIdDao.findUserById("username");
		//then
		assertEquals("username", user.getUsername());
		assertEquals("name", user.getFirstname());
		assertEquals("surname", user.getLastname());
		assertEquals("email@email.com", user.getEmail());
		assertEquals("Adress", user.getAdress());
		assertEquals("Phone Number", user.getPhoneNr());
		assertEquals(true, user.isEnabled());
	}

	@Test(expected = org.hibernate.ObjectNotFoundException.class)
	public void shouldThrow_whenIncorrectUsername() {
		//when
		Users user = loadByIdDao.findUserById("usernames");
		//then		
		assertNull(user);		
	}

	@Test
	public void shouldReturnROLE_USER_whenUserProvided(){
		//when
		Users user = loadByIdDao.findUserById("username");
		Set<UserRoles> userRoles = user.getUserRoles();
		String[] userRole = userRoles.stream().map(s->s.getRole()).toArray(String[]::new);
		//then
		assertEquals(Arrays.toString(userRole),"[ROLE_USER]");
	}

	@Test
	public void shouldReturnROLE_ADMIN_whenAdminProvided(){
		//when
		Users user = loadByIdDao.findUserById("admin");
		Set<UserRoles> userRoles = user.getUserRoles();
		String[] userRole = userRoles.stream().map(s->s.getRole()).toArray(String[]::new);
		//then
		assertThat(userRole, arrayContainingInAnyOrder("ROLE_USER","ROLE_ADMIN"));
	}
}
