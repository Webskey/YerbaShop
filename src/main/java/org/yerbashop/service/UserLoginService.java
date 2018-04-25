package org.yerbashop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.LoadByIdDao;
import org.yerbashop.model.Users;

@Service("userDetailsService")
public class UserLoginService implements UserDetailsService {

	@Autowired
	private LoadByIdDao<Users> userDetailsDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		userDetailsDao.setClazz("org.yerbashop.model.Users");

		Users user = userDetailsDao.findUserById(username);
		UserBuilder builder = null;
		if (user != null) {

			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.disabled(!user.isEnabled());
			builder.password(user.getPassword());

			user.getUserRoles();
			String[] authList = user.getUserRoles().stream().map(s->s.getRole()).toArray(String[]::new);

			builder.authorities(authList);
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}
}