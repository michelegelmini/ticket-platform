package org.lessons.java.ticket_platform.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import org.lessons.java.ticket_platform.model.User;

import org.lessons.java.ticket_platform.repo.UserRepository;
import org.lessons.java.ticket_platform.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Optional<User> user = userRepository.findByUsername(username);

		
		if (user.isPresent()) {
			return new DatabaseUserDetails(user.get());
		} else
			throw new UsernameNotFoundException("Username " + username + " not found!");

	}
	
	public List<User> findAllSortedById(){
		return userRepository.findAll(Sort.by("id"));
	}

	public List<User> searchByUsername(String username) {
		return userRepository.findByUsernameContainingIgnoreCaseOrderByIdAsc(username);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
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
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	
}


