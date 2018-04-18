package org.yerbashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.LoadByIdDao;
import org.yerbashop.dummybuilders.UsersModelBuilder;
import org.yerbashop.model.Users;
import org.yerbashop.model.UsersDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

	@Mock
	private LoadByIdDao<Users> userDetailsDao;

	@InjectMocks
	private UserProfileService userProfileService;

	private UsersDTO userDTO;
	private Users user;

	@Before
	public void setUp() {
		UsersModelBuilder userModelBuilder1 = new UsersModelBuilder(Users.class);
		user = (Users) userModelBuilder1.getObject();
		when(userDetailsDao.findUserById("Username")).thenReturn(user);
	}

	@Test
	public void shouldPass_whenUserExists() {		
		userDTO = userProfileService.getUser("Username");
		assertEquals(userDTO.getClass(), UsersDTO.class);	
		assertEquals(userDTO.getOrders().isEmpty(), true);
		assertEquals(userDTO.getFirstname(), "firstname");			
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerException_whenUserDoesNotExist() {
		UsersDTO nullUser = userProfileService.getUser("BadUser");
		assertNull(nullUser.getUsername());
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerException_whenPassedNull() {
		UsersDTO nullUser = userProfileService.getUser(null);
		assertNull(nullUser.getUsername());
	}
}
