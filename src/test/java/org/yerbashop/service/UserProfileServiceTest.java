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
		when(userDetailsDao.findUserById("username")).thenReturn(user);
	}

	@Test
	public void shouldReturnUserDetails_whenUserExists() {		
		//given
		String username = "username";
		//when
		userDTO = userProfileService.getUser(username);
		//then
		assertEquals(userDTO.getClass(), UsersDTO.class);	
		assertEquals(userDTO.getOrders().isEmpty(), true);
		assertEquals(userDTO.getFirstname(), "firstname");			
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerException_whenUserDoesNotExist() {
		//given
		String username = "W R O N G";
		//when
		UsersDTO nullUser = userProfileService.getUser(username);
		//then
		assertNull(nullUser.getUsername());
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerException_whenUserNull() {
		//given
		String username = null;
		//when
		UsersDTO nullUser = userProfileService.getUser(username);
		//then
		assertNull(nullUser.getUsername());
	}
}
