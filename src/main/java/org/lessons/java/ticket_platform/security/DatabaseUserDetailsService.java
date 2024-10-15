package org.lessons.java.ticket_platform.security;

import java.util.List;
import java.util.Optional;

import org.lessons.java.ticket_platform.model.User;
import org.lessons.java.ticket_platform.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	
	public List<User> findAllSortedById(){
		return userRepository.findAll(Sort.by("id"));
	}

	public List<User> findByUsername(String username) {
		return (List<User>) userRepository.findByUsernameContainingIgnoreCaseOrderByIdAsc(username);
	}

	public User create(User user) {
		return userRepository.save(user);
	}

	public User update(User user) {
		return userRepository.save(user);
	}  

	public User findById(Integer id) {
		return userRepository.findById(id).get();
	}

	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
	
	
	
}
