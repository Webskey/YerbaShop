package org.yerbashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.SaveDao;
import org.yerbashop.dummybuilders.UsersModelBuilder;
import org.yerbashop.model.UsersValidate;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterServiceTest {

	@Mock
	private SaveDao saveDao;

	@InjectMocks
	private UserRegisterService userRegisterService;

	private UsersValidate userValidate;

	@Before
	public void setUp() {
		UsersModelBuilder usersModelBuilder = new UsersModelBuilder(UsersValidate.class);
		userValidate = (UsersValidate) usersModelBuilder.getObject();
	}

	@Test
	public void shouldMatchUsersDetailsExceptPassword_whenAllDataProvidedCorrectly()throws Exception{
		//given
		doNothing().when(saveDao).save(any());
		//when
		userRegisterService.register(userValidate);
		//then
		assertEquals(userValidate.getUsername(), userRegisterService.getUsers().getUsername());
		assertEquals(userValidate.getFirstname(), userRegisterService.getUsers().getFirstname());
		assertEquals(userValidate.getLastname(), userRegisterService.getUsers().getLastname());
		assertEquals(userValidate.getEmail(), userRegisterService.getUsers().getEmail());
		assertEquals(userValidate.getPhoneNr(), userRegisterService.getUsers().getPhoneNr());
		assertEquals(userValidate.getAdress(), userRegisterService.getUsers().getAdress());
		assertNotEquals(userValidate.getPassword(), userRegisterService.getUsers().getPassword());

		verify(saveDao, times(2)).save(any());
	}

	@Test
	public void shouldReturnUsersRoles_whenMethodSaveInvoked()throws Exception {
		//given
		doNothing().when(saveDao).save(any());
		//when
		userRegisterService.register(userValidate);
		//then
		assertEquals(userRegisterService.getUsers(),userRegisterService.getUserRoles().getUsers());
		assertEquals(userValidate.getUsername(),userRegisterService.getUserRoles().getUsers().getUsername());
		assertEquals("ROLE_USER",userRegisterService.getUserRoles().getRole());

		verify(saveDao, times(2)).save(any());
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerException_whenUserNull() throws Exception{
		//when
		userRegisterService.register(null);
		//then NullPointerException thrown
	}

}
