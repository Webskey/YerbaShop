package org.yerbashop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.UserDetailsDa;
import org.yerbashop.model.Users;

@Service
public class UserProfileService {

	@Autowired
	private UserDetailsDa userDetailsDao;
	
	@Transactional(readOnly = true)
	public Users getUser(String username){
		
		return userDetailsDao.findUserByUsername(username);
	}
}
