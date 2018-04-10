package org.yerbashop.dummybuilders;

import org.yerbashop.model.Users;

public class UsersBuilder {
	private Users user;
	
	public UsersBuilder() {
		user = new Users();
		user.setUsername("username");
		user.setPassword("password");
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setAdress("adress");
		user.setEmail("email@email.com");
		user.setPhoneNr("phoneNr");
		user.setEnabled(true);
	}
	
	public Users getUser() {
		return user;
	}
}
