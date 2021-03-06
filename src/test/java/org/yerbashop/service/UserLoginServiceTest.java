package org.yerbashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.yerbashop.dao.LoadByIdDao;
import org.yerbashop.dummybuilders.UsersModelBuilder;
import org.yerbashop.model.UserRoles;
import org.yerbashop.model.Users;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginServiceTest {

	@Mock
	private LoadByIdDao<Users> userDetailsDao;

	@InjectMocks
	private UserLoginService userDetailsService;

	private Users user;

	@Before
	public void setUp() {
		UsersModelBuilder usersBuilder = new UsersModelBuilder(Users.class);
		user = (Users) usersBuilder.getObject();

		UserRoles userRols = new UserRoles();
		userRols.setUsers(user);
		userRols.setRole("ROLE_USER");
		Set<UserRoles> userRoles = new HashSet<>();
		userRoles.add(userRols);
		user.setUserRoles(userRoles);
	}

	@Test
	public void shouldMatchUsersDetails_whenAllDataProvidedCorrectly() throws Exception{
		//given
		when(userDetailsDao.findUserById("username")).thenReturn(user);
		//when
		UserDetails userDetails = userDetailsService.loadUserByUsername("username");
		//then
		assertEquals("password", userDetails.getPassword());
		assertEquals("username", userDetails.getUsername());
		assertEquals("[ROLE_USER]", userDetails.getAuthorities().toString());
		assertEquals(true, userDetails.isEnabled());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void shouldThrowException_whenUserIsNull() throws Exception{
		//given
		when(userDetailsDao.findUserById(any())).thenReturn(null);
		//when
		UserDetails userDetails = userDetailsService.loadUserByUsername("username");
		//then
		assertNull(userDetails);
	}
}
