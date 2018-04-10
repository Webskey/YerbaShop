package org.yerbashop.dummybuilders;

import org.yerbashop.model.UsersDTO;

public class UsersDTOBuilder {
	
	private UsersDTO user;
	
	public UsersDTOBuilder() {
		user = new UsersDTO();
		user.setUsername("username");
		user.setPassword("password");
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setEmail("email@email.com");
		user.setPhoneNr("123789456");
	}
	
	public UsersDTO getUsers() {
		return user;
	}
}
