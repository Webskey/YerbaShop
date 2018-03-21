package org.yerbashop.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.yerbashop.dao.UserDetailsDa;
import org.yerbashop.model.Users;
import org.yerbashop.service.UserProfileService;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {
	
    @Mock
    private UserDetailsDa userDetailsDa;

    @InjectMocks
    private UserProfileService userProfileService;
    
    private Users user;
    
    @Before
    public void setUp() {
    	user = new Users();
    	user.setUsername("TestUsername");
    	when(userDetailsDa.findUserByUsername("TestUsername")).thenReturn(user);
    }
    
    @Test
    public void shouldPass_whenUserExists(){
        assertEquals(userProfileService.getUser("TestUsername"),user);
        assertEquals(user.getUsername(),"TestUsername");
    }
    
    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException_whenUserDoesNotExist() {
    	Users nullUser = userProfileService.getUser("BadUser");
    	assertNull(nullUser.getUsername());
    }
    
    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException_whenPassedNull() {
    	Users nullUser = userProfileService.getUser(null);
    	assertNull(nullUser.getUsername());
    }
}
