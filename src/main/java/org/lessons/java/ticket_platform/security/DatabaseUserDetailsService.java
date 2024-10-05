package org.lessons.java.ticket_platform.security;

import java.util.Optional;

import org.lessons.java.ticket_platform.model.User;
import org.lessons.java.ticket_platform.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//cerca nel database se esiste un utente con quell'username
		Optional<User> user = userRepository.findByUsername(username);

		//se esiste costruisci una nuova istanza con i dettagli dell'utente corrispondente, altrimenti lancia l'eccezione username not found
		if (user.isPresent()) {
			return new DatabaseUserDetails(user.get());
		} else
			throw new UsernameNotFoundException("Username " + username + " not found!");

	}

}
