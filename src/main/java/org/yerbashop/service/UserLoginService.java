package org.yerbashop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yerbashop.dao.UserDetailsDao;
import org.yerbashop.model.Users;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserDetailsDao userDetailsDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users user = userDetailsDao.findUserByUsername(username);
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