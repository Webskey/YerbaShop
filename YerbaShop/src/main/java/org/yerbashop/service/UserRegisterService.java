package org.yerbashop.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.SaveDao;
import org.yerbashop.model.UserRoles;
import org.yerbashop.model.Users;
import org.yerbashop.model.UsersDTO;

@Service
public class UserRegisterService {

	@Autowired
	private SaveDao saveDao;
	
	private Users user;
	private UserRoles userRoles;
	
	@Transactional
	public void register(UsersDTO userDTO) throws ConstraintViolationException{
		
		this.user = new Users();
		user.setUsername(userDTO.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		user.setEmail(userDTO.getEmail());
		user.setAdress(userDTO.getAdress());
		user.setPhoneNr(userDTO.getPhoneNr());
		user.setEnabled(true);
		saveDao.save(user);
		
		this.userRoles = new UserRoles();
		userRoles.setUsers(user);
		userRoles.setRole("ROLE_USER");
		saveDao.save(userRoles);
	}
	
	public Users getUsers() {
		return this.user;
	}
	public UserRoles getUserRoles() {
		return this.userRoles;
	}
}
