package org.yerbashop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.LoadByIdDao;
import org.yerbashop.model.Users;

@Service
public class UserProfileService {

	@Autowired
	private LoadByIdDao<Users> loadByIdDao;

	@Transactional(readOnly = true)
	public Users getUser(String username){
		loadByIdDao.setClazz("org.yerbashop.model.Users");
		return loadByIdDao.findUserById(username);
	}
}
