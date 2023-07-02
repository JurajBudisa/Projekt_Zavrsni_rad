package hr.mev.zavrsnirad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import hr.mev.zavrsnirad.model.CustomUserDetails;
import hr.mev.zavrsnirad.model.User;
import hr.mev.zavrsnirad.repository.KorisnikRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private KorisnikRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}
	
}
