package org.yerbashop.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.LoadByIdDao;
import org.yerbashop.dao.UpdateDao;
import org.yerbashop.model.Users;
import org.yerbashop.model.UsersDTO;

@Service
public class UserProfileService {

	@Autowired
	private LoadByIdDao<Users> loadByIdDao;

	@Autowired
	private UpdateDao updateDao;

	@Transactional(readOnly = true)
	public UsersDTO getUser(String username){
		loadByIdDao.setClazz("org.yerbashop.model.Users");
		Users user = loadByIdDao.findUserById(username);
		UsersDTO usersDTO = new UsersDTO();
		usersDTO.setUsername(user.getUsername());
		usersDTO.setFirstname(user.getFirstname());
		usersDTO.setLastname(user.getLastname());
		usersDTO.setEmail(user.getEmail());
		usersDTO.setAdress(user.getAdress());
		usersDTO.setPhoneNr(user.getPhoneNr());
		usersDTO.setOrders(user.getOrders().stream().sorted((a, b) -> Integer.compare(a.getId(), b.getId())).collect(Collectors.toList()));

		return usersDTO;
	}

	@Transactional(readOnly = true)
	public void update(UsersDTO usersDTO) {
		loadByIdDao.setClazz("org.yerbashop.model.Users");
		Users user = loadByIdDao.findUserById(usersDTO.getUsername());
		user.setFirstname(usersDTO.getFirstname());
		user.setLastname(usersDTO.getLastname());
		user.setEmail(usersDTO.getEmail());
		user.setAdress(usersDTO.getAdress());
		user.setPhoneNr(usersDTO.getPhoneNr());
		updateDao.update(user);
	}
}
