package hawk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import hawk.entities.UsersInfo;
import hawk.jparepositorys.UsersInfoRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersInfoRepository usersInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsersInfo user = usersInfoRepository.findByUser(username, 0);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		return new MyUserDetails(user);
	}

}
