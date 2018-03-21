package org.yerbashop.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.SaveDao;
import org.yerbashop.model.UsersDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterServiceTest {
	
    @Mock
    private SaveDao saveDao;

    @InjectMocks
    private UserRegisterService userRegisterService;
    
    private UsersDTO userDTO;
    
    @Before
    public void setUp() {
    	userDTO = new UsersDTO();
    	userDTO.setUsername("username");
    	userDTO.setPassword("password");
    	userDTO.setFirstname("firstname");
    	userDTO.setLastname("lastname");
    	userDTO.setEmail("email@email.com");
    	userDTO.setAdress("adress");
    	userDTO.setPhoneNr("phoneNr");
    }
    
    @Test
    public void shouldMatchUsersDetailsExceptPassword_whenAllDataProvidedCorrectly()throws Exception{
    	doNothing().when(saveDao).save(any());
    	
        userRegisterService.register(userDTO);
        
        assertEquals(userDTO.getUsername(),userRegisterService.getUsers().getUsername());
        assertEquals(userDTO.getFirstname(),userRegisterService.getUsers().getFirstname());
        assertEquals(userDTO.getLastname(),userRegisterService.getUsers().getLastname());
        assertEquals(userDTO.getEmail(),userRegisterService.getUsers().getEmail());
        assertEquals(userDTO.getPhoneNr(),userRegisterService.getUsers().getPhoneNr());
        assertEquals(userDTO.getAdress(),userRegisterService.getUsers().getAdress());
        assertNotEquals(userDTO.getPassword(),userRegisterService.getUsers().getPassword());
        
    	verify(saveDao, times(2)).save(any());
    }
    
    @Test
    public void shouldReturnUsersRoles_whenMethodSaveInvoked()throws Exception {
    	doNothing().when(saveDao).save(any());
    	
    	userRegisterService.register(userDTO);
    	
    	assertEquals(userRegisterService.getUsers(),userRegisterService.getUserRoles().getUsers());
    	assertEquals(userDTO.getUsername(),userRegisterService.getUserRoles().getUsers().getUsername());
    	assertEquals("ROLE_USER",userRegisterService.getUserRoles().getRole());
    	
    	verify(saveDao, times(2)).save(any());
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void shouldConstraintViolationException_whenUserAlreadytExists() throws Exception{
    	doThrow(ConstraintViolationException.class).when(saveDao).save(any());
    	userRegisterService.register(userDTO);
    }
    
    @Test(expected = NullPointerException.class)
    public void shouldNullPointerException_whenPassedNull() throws Exception{
    	userRegisterService.register(null);
    }

}
